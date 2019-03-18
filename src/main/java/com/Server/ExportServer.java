package com.Server;

import org.postgresql.copy.CopyManager;
import org.postgresql.core.BaseConnection;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportServer {

    public static void main(String[] args) {

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "postgres";

        try {

            Connection con = DriverManager.getConnection(url, username, password);
            CopyManager cm = new CopyManager((BaseConnection) con);

            String fileName = "src/main/resources/friends";

            try (FileOutputStream fos = new FileOutputStream(fileName);
                 OutputStreamWriter osw = new OutputStreamWriter(fos,
                         StandardCharsets.UTF_8)) {

                cm.copyOut("COPY question TO STDOUT WITH DELIMITER AS '|'", osw);
            }

            System.out.println(cm);

        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());

            Logger lgr = Logger.getLogger(
                    ExportServer.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}
