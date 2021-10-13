package trvoid.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class BloomFilterApplication implements CommandLineRunner {
	@Autowired
	DataSource dataSource;

	@Autowired
	RejectMapper rejectMapper;

	public static void main(String[] args) {
		SpringApplication.run(BloomFilterApplication.class, args);
	}

	private static void printLog(String s) {
		System.out.println(String.format("[%s] %s", DateTimeUtils.getFormattedLongDateTimeNow(), s));
	}

	private void initRejectTable() {
		Reject reject1 = new Reject("08000000001", "01000000001");
		rejectMapper.insertReject(reject1);
		System.out.println(String.format("** Inserted %s", reject1.toString()));

		Reject reject2 = new Reject("08000000001", "01000000002");
		rejectMapper.insertReject(reject2);
		System.out.println(String.format("** Inserted %s", reject2.toString()));

		Reject reject3 = new Reject("08000000002", "01000000001");
		rejectMapper.insertReject(reject3);
		System.out.println(String.format("** Inserted %s", reject3.toString()));
	}

	private int insertSampleRejects(String fromPhoneNo, String toPhoneNoBase, int count) {
		long toPhoneNo = Long.parseLong(toPhoneNoBase);

		int insertedCount = 0;

		for (int i = 0; i < count; i++) {
			String toPhoneNoNew = String.format("%011d", toPhoneNo + i);
			Reject reject = new Reject(fromPhoneNo, toPhoneNoNew);

			rejectMapper.insertReject(reject);

			insertedCount++;
		}

		return insertedCount;
	}

	private void insertTestData() {
		int insertedCount = 0;

		insertedCount = insertSampleRejects("08000000001", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000002", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000003", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000004", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000005", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000006", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000007", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000008", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000009", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));

		insertedCount = insertSampleRejects("08000000010", "01000000001", 10000);
		System.out.println(String.format("Inserted %d rows.", insertedCount));
	}

	private BloomFilter<String> initBloomFilter(int expectedInsertions, double fpp) {
		printLog("Create a Bloom Filter instance.");
		BloomFilter<String> rejectFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")),
				expectedInsertions,
				fpp);

		printLog("Add all the REJECT data.");
		List<Reject> list = rejectMapper.findAll();
		list.forEach(x -> rejectFilter.put(x.getFromPhoneNo() + x.getToPhoneNo()));

		return rejectFilter;
	}

	private void verifyBloomFilter(BloomFilter<String> rejectFilter) {
		printLog("Verify the Bloom Filter.");
		String[] values = new String[] {
			"0800000000101000000001",
			"0800000000102000000001"
		};

		for (String s : values) {
			System.out.println(String.format("%s -> %s", s, rejectFilter.mightContain(s)));
		}
	}

	private void comparePerformance(int testDataSize, BloomFilter<String> rejectFilter) {
		printLog(String.format("Prepare test data. testDataSize: %d", testDataSize));

		int initialCapacity = testDataSize;

		List<String> fromPhoneNoData = new ArrayList<String>(initialCapacity);
		List<String> toPhoneNoData = new ArrayList<String>(initialCapacity);
		Random random = new Random();
		for (int i = 0; i < testDataSize; i++) {
			fromPhoneNoData.add(String.format("080%08d", random.nextInt(100000000)));
			toPhoneNoData.add(String.format("010%08d", random.nextInt(100000000)));
		}

		printLog("Start test.");

		List<Boolean> results1 = new ArrayList<Boolean>(initialCapacity);
		List<Boolean> results2 = new ArrayList<Boolean>(initialCapacity);
		List<Boolean> results3 = new ArrayList<Boolean>(initialCapacity);

		long t1 = System.currentTimeMillis();

		for (int i = 0; i < testDataSize; i++) {
			int count = rejectMapper.countReject(fromPhoneNoData.get(i), toPhoneNoData.get(i));
			results1.add(count > 0);
		}

		long t2 = System.currentTimeMillis();

		for (int i = 0; i < testDataSize; i++) {
			boolean found = rejectFilter.mightContain(fromPhoneNoData.get(i) + toPhoneNoData.get(i));
			results2.add(found);
		}

		long t3 = System.currentTimeMillis();

		for (int i = 0; i < testDataSize; i++) {
			boolean found = rejectFilter.mightContain(fromPhoneNoData.get(i) + toPhoneNoData.get(i));
			if (found) {
				int count = rejectMapper.countReject(fromPhoneNoData.get(i), toPhoneNoData.get(i));
				results3.add(count > 0);
			} else {
				results3.add(false);
			}
		}

		long t4 = System.currentTimeMillis();

		printLog("End test.");

		printLog(String.format("t1: %s", DateTimeUtils.getFormattedLongDateTime(t1)));
		printLog(String.format("t2: %s", DateTimeUtils.getFormattedLongDateTime(t2)));
		printLog(String.format("t3: %s", DateTimeUtils.getFormattedLongDateTime(t3)));
		printLog(String.format("t4: %s", DateTimeUtils.getFormattedLongDateTime(t4)));

		printLog("== Result Analysis ==");
		int actualPositiveCount = 0;
		int falsePositiveCount = 0;
		for (int i = 0; i < testDataSize; i++) {
			if (results1.get(i) == true) {
				actualPositiveCount++;
			} else {
				if (results2.get(i) == true) {
					falsePositiveCount++;
				}
			}
		}

		double falsePositiveRate = (double) falsePositiveCount / testDataSize;

		printLog(String.format("Actual Positive Count: %d", actualPositiveCount));
		printLog(String.format("False Positive Count: %d", falsePositiveCount));
		printLog(String.format("False Positive Rate: %.4f", falsePositiveRate));
	}

	@Override
	public void run(String... args) throws Exception {
		printLog("DataSource = " + dataSource);

		if (rejectMapper.countTable() == 0) {
			rejectMapper.createTable();
			printLog("** Created a table: REJECT");
		}

		//insertTestData();

		int expectedInsertions = 100000;
		double fpp = 0.01;

		BloomFilter<String> rejectFilter = initBloomFilter(expectedInsertions, fpp);
		verifyBloomFilter(rejectFilter);

		int testDataSize = 1000000;

		comparePerformance(testDataSize, rejectFilter);

		System.exit(0);
	}
}
