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
import tikape.runko.domain.Vastaus;

/**
 *
 * @author papirila
 */
public class VastausDao implements Dao<Vastaus, Integer>{
    
    private Database database;

    public VastausDao(Database database) {
        this.database = database;
    }

    @Override
    public Vastaus findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer id = rs.getInt("id");
        String vastausteksti = rs.getString("vastausteksti");
        Boolean oikein = rs.getBoolean("oikein");
        Integer kysymys_id=rs.getInt("kysymys_id");

        Vastaus v= new Vastaus(id, vastausteksti, oikein, kysymys_id);

        rs.close();
        stmt.close();
        connection.close();

        return v;
    }

    @Override
    public List<Vastaus> findAll() throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus");                

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String vastausteksti = rs.getString("vastausteksti");
            Boolean oikein = rs.getBoolean("oikein");
            Integer kysymys_id=rs.getInt("kysymys_id");
            
            vastaukset.add(new Vastaus(id, vastausteksti, oikein, kysymys_id));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }

    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
//    @Override
//    public Vastaus saveOrUpdate(Vastaus object) throws SQLException {
//        
//        Connection conn=database.getConnection();
//        PreparedStatement stmt =conn.prepareStatement("INSERT INTO Vastaus(vastausteksti, oikein) VALUES(?,?)");
//        stmt.setString(1, object.getVastausteksti());
//        stmt.setBoolean(2, object.getOikein());
//        
//        stmt.executeUpdate();
//        
//        
//        
//        PreparedStatement query =conn.prepareStatement("SELECT id, vastausteksti, oikein FROM Vastaus WHERE id=(SELECT MAX(id) FROM Vastaus)");
//        ResultSet rs= query.executeQuery();
//        rs.next();
//        object= new Vastaus( rs.getInt("id"), rs.getString("vastausteksti"), rs.getBoolean("oikein"));
//        conn.close();
//        
//        return null;
//        
//    }
    
    public List<Vastaus> findAllVastaukset(Integer kysymysId) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM Vastaus WHERE kysymys_id = ?");
        stmt.setInt(1, kysymysId);
        

        ResultSet rs = stmt.executeQuery();
        List<Vastaus> vastaukset = new ArrayList<>();
        while (rs.next()) {
            Integer id = rs.getInt("id");
            String vastausteksti = rs.getString("vastausteksti");
            Boolean oikein = rs.getBoolean("oikein");
            Integer kysymys_id=rs.getInt("kysymys_id");
            
            vastaukset.add(new Vastaus(id, vastausteksti, oikein, kysymys_id));
        }

        rs.close();
        stmt.close();
        connection.close();

        return vastaukset;
    }
    
    
}
