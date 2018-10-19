/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Kysymys;


/**
 *
 * @author papirila
 */
public class KysymyksetDao implements Dao<Kysymys, Integer>{
    
    private Database database;

    public KysymyksetDao(Database database) {
        this.database = database;
    }

    @Override
    public Kysymys findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String kurssi = rs.getString("kurssi");
        String aihe = rs.getString("aihe");
        String kysymysteksti = rs.getString("kysymysteksti");

        Kysymys k = new Kysymys(id, kurssi, aihe, kysymysteksti);

        rs.close();
        stmt.close();
        connection.close();

        return k;
    }

    @Override
    public List<Kysymys> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Kysymys");

        ResultSet rs = stmt.executeQuery();
        List<Kysymys> kysymykset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String kurssi = rs.getString("kurssi");
            String aihe = rs.getString("aihe");
            String kysymysteksti = rs.getString("kysymysteksti");

            kysymykset.add(new Kysymys(id, kurssi, aihe, kysymysteksti));
        }

        rs.close();
        stmt.close();
        connection.close();

        return kysymykset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
//    @Override
//    public Kysymys saveOrUpdate(Kysymys object) throws SQLException {
//        
//        Connection conn=database.getConnection();
//        PreparedStatement stmt =conn.prepareStatement("INSERT INTO Kysymys(kurssi, aihe, kysymysteksti) VALUES(?,?,?)");
//        stmt.setString(1, object.getKurssi());
//        stmt.setString(2, object.getAihe());
//        stmt.setString(3, object.getKysymysteksti());
//        stmt.executeUpdate();
//        
//        
//        
////        PreparedStatement query =conn.prepareStatement("SELECT id, kurssi, aihe, kysymysteksti FROM Kysymys WHERE id=(SELECT MAX(id) FROM Kysymys)");
////        ResultSet rs= query.executeQuery();
////        rs.next();
////        object= new Kysymys( rs.getInt("id"), rs.getString("kurssi"), rs.getString("aihe"), rs.getString("kysymysteksti"));
//        conn.close();
//        
//        return null;
//        
//    }
    
    
    
}
