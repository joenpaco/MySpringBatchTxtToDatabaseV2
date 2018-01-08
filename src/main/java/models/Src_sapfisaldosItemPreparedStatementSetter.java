package models;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.batch.item.database.ItemPreparedStatementSetter;

public class Src_sapfisaldosItemPreparedStatementSetter implements ItemPreparedStatementSetter<Src_sapfisaldos> {

	public void setValues(Src_sapfisaldos result, PreparedStatement ps) throws SQLException {
		ps.setString(1, result.getBukrs());
		ps.setString(2, result.getKtopl());
		ps.setString(3, result.getSaknr());
		ps.setString(4, result.getBilkt());
		ps.setString(5, result.getFisc_year());
		ps.setString(6, result.getFis_period());
		ps.setString(7, result.getDebits_per());
		ps.setString(8, result.getCredit_per());
		ps.setString(9, result.getPer_sales());
		ps.setString(10, result.getBalance());
		ps.setString(11, result.getCurrency());
	}

}