package koral.authmepremiumlogin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.URL;

public final class AuthmePremiumLogin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this,this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws IOException {
        Player p = e.getPlayer();
        getData(p.getName());
        String uuid = p.getUniqueId().toString().replaceAll("-", "");
    }

    public void getData(String name) throws IOException, NullPointerException {
        JSONParser parser = new JSONParser();
        try{
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
            if(httpsURLConnection.getResponseCode() ==200){
                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line=br.readLine();
                    JSONObject jsonObject = (JSONObject) parser.parse(line);
                    String nazwa = (String) jsonObject.get("name");
                    System.out.println(nazwa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
