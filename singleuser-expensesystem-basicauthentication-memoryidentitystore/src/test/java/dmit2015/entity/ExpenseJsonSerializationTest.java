package dmit2015.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import org.junit.jupiter.api.Test;

class ExpenseJsonSerializationTest {

	@Test
	void shouldSerialize() {
		Expense expense1 = new Expense();
		expense1.setAmount(BigDecimal.valueOf(1.11));
		expense1.setDate(LocalDate.parse("2020-04-15"));
		expense1.setDateCreated(LocalDateTime.now());
		expense1.setDescription("Expense JSON serailization test");
		
		Jsonb jsonb = JsonbBuilder.create();
		String jsonExpense = jsonb.toJson(expense1);
		System.out.println(jsonExpense);
		
		Expense deserializeExpense = jsonb.fromJson(jsonExpense, Expense.class);
		System.out.println(deserializeExpense.getDateCreated());
	}

}
