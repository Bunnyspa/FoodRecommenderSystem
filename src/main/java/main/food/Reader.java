/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.food;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Key;
import org.json.JSONObject;

/**
 *
 * @author Dongsoo Seo
 */
public class Reader {

    public static List<Food> MAP_FOOD_ID = new ArrayList<Food>() {
        {
            try (BufferedReader br = new BufferedReader(new FileReader("resources/food.dat"))) {
                Iterator<String> bri = br.lines().iterator();
                String country = "";
                while (bri.hasNext()) {
                    String line = bri.next().trim();
                    if (!line.isEmpty()) {
                        if (line.startsWith("[")) {
                            country = line.substring(1, line.length() - 1);
                        } else {
                            String[] split = line.split(",");
                            String name = split[0];
                            int id = Integer.valueOf(split[1]);
                            List<String> ingreds = new ArrayList<>(split.length - 2);
                            for (int i = 2; i < split.length; i++) {
                                ingreds.add(split[i]);
                            }
                            add(new Food(name, country, id));
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Reader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    public static JSONObject test() {
        return readFDC(MAP_FOOD_ID.get(0).id);
    }

    public static JSONObject readFDC(int id) {
        try {
            File file = new File("cache/" + id + ".json");
            if (file.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    StringBuilder out = new StringBuilder();
                    br.lines().forEach((l) -> out.append(l));
                    return new JSONObject(out.toString());
                }
            } else {
                JSONObject out = nutrition_get(id);
                if (out != null) {
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        bw.write(out.toString());
                    } catch (IOException ex) {
                    }
                    return out;
                }
            }
        } catch (IOException ex) {
        }
        return null;
    }

    private static JSONObject nutrition_get(int id) {
        try {
            URL u = new URL("https://api.nal.usda.gov/fdc/v1/food/"+ id +"?api_key=" + Key.KEY_FOODDATACENTRAL);
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");
            int code = con.getResponseCode();
            String msg = con.getResponseMessage();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return new JSONObject(response.toString());
            }
        } catch (Exception ex) {
        }
        return null;
    }

    // TODO: Photo
//    private static final Map<String, String> RECIPEID_URL = new HashMap<String, String>() {
//        {
//            try (BufferedReader br = new BufferedReader(new FileReader("resources/recipe1M/layer2.json"))) {
//                Iterator<String> bri = br.lines().iterator();
//                while (bri.hasNext()) {
//                    String line = bri.next();
//                    if (line.startsWith("{")) {
//                        JSONObject j = new JSONObject(line);
//                        String id = j.getString("id");
//                        String url = j.getJSONArray("images").getJSONObject(0).getString("url");
//                        put(id, url);
//                    }
//                }
//            } catch (IOException ex) {
//            }
//        }
//    };
}
