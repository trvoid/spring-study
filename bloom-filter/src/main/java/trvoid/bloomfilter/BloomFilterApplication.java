package trvoid.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.nio.charset.Charset;
import java.util.List;

@SpringBootApplication
public class BloomFilterApplication implements CommandLineRunner {
	@Autowired
	DataSource dataSource;

	@Autowired
	RejectMapper rejectMapper;

	public static void main(String[] args) {
		SpringApplication.run(BloomFilterApplication.class, args);
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

	private BloomFilter<String> initBloomFilter() {
		System.out.println("Create a Bloom Filter instance.");
		BloomFilter<String> rejectFilter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")),100000);

		System.out.println("Add all the REJECT data.");
		List<Reject> list = rejectMapper.findAll();
		list.forEach(x -> rejectFilter.put(x.getFromPhoneNo() + x.getToPhoneNo()));

		return rejectFilter;
	}

	private void testBloomFilter(BloomFilter<String> rejectFilter) {
		System.out.println("Test the Bloom Filter.");
		String[] values = new String[] {
			"0800000000101000000001",
			"0800000000102000000001"
		};

		for (String s : values) {
			System.out.println(String.format("%s -> %s", s, rejectFilter.mightContain(s)));
		}
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("DataSource = " + dataSource);

		if (rejectMapper.countTable() == 0) {
			rejectMapper.createTable();
			System.out.println("** Created a table: REJECT");
		}

		//insertTestData();

		BloomFilter<String> rejectFilter = initBloomFilter();
		testBloomFilter(rejectFilter);

		System.exit(0);
	}
}
