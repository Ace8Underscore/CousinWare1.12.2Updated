package io.ace.nordclient.utilz;

import io.ace.nordclient.CousinWare;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class FileManager {

    public ResourceLocation loren;
    private ICamera camera;

    public FileManager() {
    load();
    }

    public void load() {
        BufferedImage image = null;
        try {
            String imageUrl = "/assets/minecraft/custom/loren.png";
            image = this.getImage(this.getFile(imageUrl), ImageIO::read);
            if (image == null) {
                CousinWare.log.warn("Failed to load image");
            }
            else {
                final DynamicTexture dynamicTexture = new DynamicTexture(image);
                dynamicTexture.loadTexture(Minecraft.getMinecraft().getResourceManager());
                loren = Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("cousinware_" + imageUrl, dynamicTexture);
                CousinWare.log.info("Image Loaded");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    private <T> BufferedImage getImage(final T source, final ThrowingFunction<T, BufferedImage> readFunction) {
        try {
            return readFunction.apply(source);
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    @FunctionalInterface
    private interface ThrowingFunction<T, R>
    {
        R apply(final T p0) throws IOException;
    }



    private InputStream getFile(final String string) {
        return FileManager.class.getResourceAsStream(string);
    }
}
