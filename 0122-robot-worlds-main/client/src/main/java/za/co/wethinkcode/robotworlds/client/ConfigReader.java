package za.co.wethinkcode.robotworlds.client;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private String centreX;
    private String centreY;
    private String height;
    private String width;
    private String maxObstacles;
    private String minObstacles;
    private String maxBottomPits;
    private String minBottomPits;
    private String shieldRepair;
    private String weaponReload;
    private String setMine;
    private String shieldStrength;
    private String obsSize;
    private String pitsSize;
    private String minerShield;
    private String gunnerShield;
    private String tankShield;
    private String sniperShield;
    private String minerVision;
    private String gunnerVision;
    private String tankVision;
    private String sniperVision;
    private String minerBullets;
    private String gunnerBullets;
    private String tankBullets;
    private String sniperBullets;

    public ConfigReader() {
        setFields();
    }

    private void setFields () {
        String pathToConfig = ConfigReader.class.getResource("../config/config.properties").getPath();
        pathToConfig = pathToConfig.replace("file:", "");
        try (InputStream input = new FileInputStream(pathToConfig)) {
            Properties prop = new Properties();
            prop.load(input);

            this.centreX = prop.getProperty("centreX");
            this.centreY = prop.getProperty("centreY");
            this.height = prop.getProperty("height");
            this.width = prop.getProperty("width");
            this.maxObstacles = prop.getProperty("maxObstacles");
            this.minObstacles = prop.getProperty("minObstacles");
            this.maxBottomPits = prop.getProperty("maxBottomPits");
            this.minBottomPits = prop.getProperty("minBottomPits");
            this.shieldRepair = prop.getProperty("shieldRepair");
            this.weaponReload = prop.getProperty("weaponReload");
            this.setMine = prop.getProperty("setMine");
            this.shieldStrength = prop.getProperty("maxShieldStrength");
            this.obsSize = prop.getProperty("obsSize");
            this.pitsSize = prop.getProperty("pitsSize");
            this.minerShield = prop.getProperty("minerShield");
            this.gunnerShield = prop.getProperty("gunnerShield");
            this.sniperShield = prop.getProperty("sniperShield");
            this.tankShield = prop.getProperty("tankShield");
            this.minerVision = prop.getProperty("minerVision");
            this.gunnerVision = prop.getProperty("gunnerVision");
            this.tankVision = prop.getProperty("tankVision");
            this.sniperVision = prop.getProperty("sniperVision");
            this.minerBullets = prop.getProperty("minerBulletRange");
            this.gunnerBullets = prop.getProperty("gunnerBulletRange");
            this.tankBullets = prop.getProperty("tankBulletRange");
            this.sniperBullets = prop.getProperty("sniperBulletRange");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getObsSize() {
        return obsSize;
    }

    public String getPitsSize() {
        return pitsSize;
    }

    public String getCentreX() {
        return centreX;
    }

    public String getCentreY() {
        return centreY;
    }

    public String getHeight() {
        return height;
    }

    public String getWidth() {
        return width;
    }

    public String getMaxObstacles() {
        return maxObstacles;
    }

    public String getMinObstacles() {
        return minObstacles;
    }

    public String getMaxBottomPits() {
        return maxBottomPits;
    }

    public String getMinBottomPits() {
        return minBottomPits;
    }

    public String getShieldRepair() {
        return shieldRepair;
    }

    public String getWeaponReload() {
        return weaponReload;
    }

    public String getSetMine() {
        return setMine;
    }

    public String getShieldStrength() {
        return shieldStrength;
    }

    public String getMinerShield() {
        return minerShield;
    }

    public String getGunnerShield() {
        return gunnerShield;
    }

    public String getTankShield() {
        return tankShield;
    }

    public String getSniperShield() {
        return sniperShield;
    }

    public String getMinerVision() {
        return minerVision;
    }

    public String getGunnerVision() {
        return gunnerVision;
    }

    public String getTankVision() {
        return tankVision;
    }

    public String getSniperVision() {
        return sniperVision;
    }

    public String getMinerBullets() {
        return minerBullets;
    }

    public String getGunnerBullets() {
        return gunnerBullets;
    }

    public String getTankBullets() {
        return tankBullets;
    }

    public String getSniperBullets() {
        return sniperBullets;
    }
}
