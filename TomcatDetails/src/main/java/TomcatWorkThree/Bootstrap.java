package TomcatWorkThree;


import TomcatWorkThree.connector.http.HttpConnector;

public class Bootstrap {
        public static void main(String[] args) {
            HttpConnector connector = new HttpConnector();
            connector.start();
        }
    }
