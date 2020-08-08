package com.tasksOrganizer.db;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnect {
    private volatile static DBConnect single;
    private Connection conn = null;

    private DBConnect() {
        String[] params = chargerProprietes("jdbc.properties");
        conn = DBconnecter(params);
    }

    public static DBConnect getInstance() {
        if (single == null) {
            synchronized (DBConnect.class) {
                if (single == null)
                    single = new DBConnect();
            }
        }
        return single;
    }

    public Connection getConn() {
        return this.conn;
    }

    private String[] chargerProprietes(String fichierProperties) {
        // charge le fichier de proprietes et renvoie ces dernieres dans un tableau de
        // chaines de caracteres

        Properties prop = null;
        InputStream in = null;
        String url, user, password, driver, proprietes;

        try {
            prop = new Properties();
            in = getClass().getClassLoader().getResourceAsStream(fichierProperties);
            prop.load(in);
            in.close();
        } catch (Exception e) {
            System.out.println("Echec de chargement du fichier jdbc.properties");
            // e.printStackTrace();
        }

        url = prop.getProperty("jdbc.url");
        user = prop.getProperty("jdbc.user");
        password = prop.getProperty("jdbc.password");
        driver = prop.getProperty("jdbc.driver");
        proprietes = url + "°" + user + "°" + password + "°" + driver;

        return proprietes.split("°");
    }

    private static Connection DBconnecter(String[] params) {
        // se sert des parametres de connexion pour se connecter a la base de donnees et
        // renvoie l'objet Connection obtenu

        Connection conn = null;

        try {
            Class.forName(params[3]);
            conn = DriverManager.getConnection(params[0], params[1], params[2]);
        } catch (Exception e) {
            System.out.println("Echec de connection a la base de donnees");
            // e.printStackTrace();
        }
        return conn;
    }
}
