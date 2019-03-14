
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Random;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author xiaoditao
 */

public class butterflyModel {
        // The zong method is used to get the url of the website and the url of the picture
        public String zong(String speciesToSearch, String regionToSearch,String stageToSearch,String picSize) throws UnsupportedEncodingException{
            if(speciesToSearch==null || speciesToSearch.equals("all") ){
            speciesToSearch = "";
        }
        speciesToSearch = URLEncoder.encode(speciesToSearch, "UTF-8");
        // set the default value of the textfields and radio buttons
        if(stageToSearch.equals("all")){
            stageToSearch = "All";
        }else{
        stageToSearch = URLEncoder.encode(stageToSearch, "UTF-8");
        }
        if(regionToSearch.equals("all")) {
            regionToSearch="All";
        }
        regionToSearch = URLEncoder.encode(regionToSearch, "UTF-8");
        // set the response String to get the page source of the website
        String response = "";
        // set the string to get the url of the web we want to do scraping
        String flickrURL =
        "https://www.butterfliesandmoths.org/gallery?species="+speciesToSearch+"&family=All&subfamily=All&type=All&view=All&stage="+
                stageToSearch+"&region="+regionToSearch;
        String res=flickrURL;
        return res;
        }
        
        // the doFlikrSearch method is used to get the picture url and parse it to the servlet
        public String doFlickrSearch(String speciesToSearch, String regionToSearch,String stageToSearch,String picSize) 
            throws UnsupportedEncodingException  {
        if(speciesToSearch==null || speciesToSearch.equals("all") ){
            speciesToSearch = "";
        }
        // set the default value of the textfields and radio buttons
        speciesToSearch = URLEncoder.encode(speciesToSearch, "UTF-8");
        if(stageToSearch.equals("all")){
            stageToSearch = "All";
        }
        if(stageToSearch == null){
            stageToSearch = "";
        }
        stageToSearch = URLEncoder.encode(stageToSearch, "UTF-8");
        if(regionToSearch.equals("all")) {
            regionToSearch="All";
        }
        if(regionToSearch == null){
            regionToSearch = "";
        }
        regionToSearch = URLEncoder.encode(regionToSearch, "UTF-8");
        String response = "";
        String flickrURL = "http://www.google.com";
        // get the url of the website that we want to do scraping
        flickrURL =
        "https://www.butterfliesandmoths.org/gallery?species="+speciesToSearch+"&family=All&subfamily=All&type=All&view=All&stage="+
                stageToSearch+"&sex=All&region="+regionToSearch;
        // set a string called response to get the page source 
        response = fetch(flickrURL);
        int cutLeftFirst;
        int lastLeft;
        int cutRight;
        // set random to randomly select the picture
        // get the first and last picture of the website, compute how many picures are between them
        // then base on the number of the picture, randomly scrap picture
        // if there are more than 40 picures, only random the first 40 pictures
        Random rd = new Random();
        if(picSize.equals("mobile")){
        cutLeftFirst = response.indexOf("<img typeof=\"foaf:Image\" class=\"img-responsive\" src=\"");
        if (cutLeftFirst == -1) {
            return (String) null;
        }
        lastLeft = response.lastIndexOf("<img typeof=\"foaf:Image\" class=\"img-responsive\" src=\"");
        int addUp = 0;
        if((lastLeft - cutLeftFirst - 40000) >  0) {
            addUp = rd.nextInt(35)+1;
        }else {
            int num = (lastLeft - cutLeftFirst)/1000;
            addUp = rd.nextInt(num)+1;
             }
        response = response.substring(addUp * 1000 + cutLeftFirst);
        cutLeftFirst = response.indexOf("<img typeof=\"foaf:Image\" class=\"img-responsive\" src=\"");
        if (cutLeftFirst == -1) {
            cutLeftFirst=lastLeft;
        } 
        cutLeftFirst += "<img typeof=\"foaf:Image\" class=\"img-responsive\" src=\"".length();
        cutRight = response.indexOf("\"", cutLeftFirst);
        }else{
        cutLeftFirst = response.indexOf("<div class=\"field-content\"><a href=\"");
        // if there is no picture, then retrun null
        if (cutLeftFirst == -1) {
            return (String) null;
        }
        // get the first picture's left cut
        lastLeft = response.lastIndexOf("<div class=\"field-content\"><a href=\"");
        int addUp = 0;
        // get the last picture's left cut, if there are more than 40 pictures between the first and last picture, set the random smaller than 40
        if((lastLeft - cutLeftFirst - 40000) >  0) {
            addUp = rd.nextInt(35)+1;
        }else {
            int num = (lastLeft - cutLeftFirst)/1000;
            addUp = rd.nextInt(num)+1;
             }
        response = response.substring(addUp * 1000 + cutLeftFirst);
        cutLeftFirst = response.indexOf("<div class=\"field-content\"><a href=\"");
        if (cutLeftFirst == -1) {
            cutLeftFirst=lastLeft;
        }
        cutLeftFirst += "<div class=\"field-content\"><a href=\"".length();
        cutRight = response.indexOf("\"", cutLeftFirst);
        }
        String pictureURL = response.substring(cutLeftFirst, cutRight);
        return pictureURL;
    }
       
               
        
        
private String fetch(String searchURL) {
        try {
            createTrustManager();
        } catch (KeyManagementException ex) {
            ex.printStackTrace();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }   
        // use a string called response to get the page source
        String response = "";
        try {
            URL url = new URL(searchURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            while ((str = in.readLine()) != null) {
                response += str;
            }
            in.close();
          // if we cannot get the searchURL, e.g. turn off the wifi, or when the source page crashed
          // we will return a non-null value to response
        } catch (IOException e) {
            return "hi";
        }
         return response;
    }
    
    private void createTrustManager() throws KeyManagementException, NoSuchAlgorithmException{
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
        };
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
    }


}
