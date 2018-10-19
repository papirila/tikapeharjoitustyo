package tikape.runko;

import java.util.HashMap;
import static javax.xml.bind.DatatypeConverter.parseBoolean;
import spark.ModelAndView;
import static spark.Spark.*;
import tikape.runko.database.Database;
import tikape.runko.database.KysymyksetDao;
import tikape.runko.database.VastausDao;
import spark.ModelAndView;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import spark.Spark;
import tikape.runko.domain.Kysymys;
import tikape.runko.domain.Vastaus;
import java.sql.*;

public class Main {

    public static void main(String[] args) throws Exception {
        if (System.getenv("PORT") != null) {
            Spark.port(Integer.valueOf(System.getenv("PORT")));
        }
        
        Database database = new Database("jdbc:sqlite:kysymykset.db");
        database.init();

        KysymyksetDao kDao = new KysymyksetDao(database);
        VastausDao vDao= new VastausDao(database);



        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("kysymykset", kDao.findAll());

            return new ModelAndView(map, "index");
            
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/kysymykset", (req,res)-> {
            
            HashMap mappi = new HashMap<>();
            mappi.put("kysymykset", kDao.findAll());
            
            mappi.put("vastaukset", vDao.findAll());
            
            return new ModelAndView(mappi, "kysymykset");
        }, new ThymeleafTemplateEngine());
        
        Spark.get("/kysymys/:id", (req, res) -> {
            
            HashMap mappi = new HashMap<>();
            mappi.put("kysymys", kDao.findOne(Integer.parseInt(req.params("id"))));
            
            

            mappi.put("vastaukset", vDao.findAllVastaukset(Integer.parseInt(req.params("id"))));

            return new ModelAndView(mappi, "kysymys");
        }, new ThymeleafTemplateEngine());
        
        
        Spark.post("/lisaakysymys", (req, res)->{
//            kDao.saveOrUpdate(new Kysymys(Integer.parseInt(req.params("id")),req.queryParams("kurssi"), req.queryParams("aihe"), req.queryParams("kysymysteksti")));
            Connection conn=database.getConnection();
            PreparedStatement stmt =conn.prepareStatement("INSERT INTO Kysymys(kurssi, aihe, kysymysteksti) VALUES(?,?,?)");
            stmt.setString(1, req.queryParams("kurssi"));
            stmt.setString(2, req.queryParams("aihe"));
            stmt.setString(3, req.queryParams("kysymysteksti"));
            stmt.executeUpdate();

            res.redirect("/");
            return "";
        });
        
        Spark.post("/lisaavastaus", (req, res)->{
            Connection conn=database.getConnection();
            PreparedStatement stmt =conn.prepareStatement("INSERT INTO Vastaus(vastausteksti, oikein, kysymys_id) VALUES(?,?,?)");
            stmt.setString(1, req.queryParams("vastausteksti"));
            String oikein=req.queryParams("oikein");
            if (oikein.equals("kyllä")) {
                stmt.setBoolean(2, true);
                
            } else {
                stmt.setBoolean(2, false);
                
            }
            stmt.setInt(3, Integer.parseInt(req.queryParams("kysymys_id")));
            stmt.executeUpdate();
            
            res.redirect("/");
            return "";
        });
        
        
        
        
        Spark.get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("vastaukset", vDao.findAll());

            return new ModelAndView(map, "index");
            
        }, new ThymeleafTemplateEngine());
        
         
        
        
        Spark.post("/poistakysymys", (req, res)->{
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Kysymys WHERE id = ?");

            stmt.setInt(1, Integer.parseInt(req.queryParams("kysymys.id")));
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            
            res.redirect("/kysymykset");
            return "";
        });
        
        
        
        
        Spark.post("/poistavastaus", (req, res)->{
            
            Connection conn = database.getConnection();
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM Vastaus WHERE id = ?");

            stmt.setInt(1, Integer.parseInt(req.queryParams("vastaus.id")));
            stmt.executeUpdate();

            stmt.close();
            conn.close();
            
            res.redirect("/kysymykset");
            return "";
        });
    }
}
