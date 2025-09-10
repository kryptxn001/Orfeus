package me.torhash.orfeus.ui.uielements;

import me.torhash.orfeus.Orfeus;
import me.torhash.orfeus.ui.UIElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Direction;
import net.minecraft.world.dimension.DimensionType;

public class MainHUD extends UIElement {
    public MainHUD() {
        super("MainHUD", true);
    }

    @Override
    protected void onRender(MatrixStack matrixStack) {
        matrixStack.push();
        matrixStack.scale(2,2,2);
        textRenderer.drawWithShadow(matrixStack, Orfeus.Instance.name,2,2, 0xFFFFFF);
        textRenderer.drawWithShadow(matrixStack, " v"+Orfeus.Instance.version, 2 + textRenderer.getWidth(Orfeus.Instance.name),2,Orfeus.Instance.colorUtils.getColor());
        matrixStack.pop();

        double posX= mc.player.getX();
        double posY= mc.player.getY();
        double posZ= mc.player.getZ();

        if(mc.world.getDimension().isBedWorking()) {
            textRenderer.drawWithShadow(matrixStack,"X: "+Math.floor(posX*10/8)/10+" Y: "+Math.floor((posY)*10)/10+" Z: "+Math.floor(posZ*10/8)/10,2, mc.getWindow().getScaledHeight()-4-textRenderer.fontHeight*2,0xFF0000);
            textRenderer.drawWithShadow(matrixStack,"X: "+Math.floor(posX*10)/10+" Y: "+Math.floor(posY*10)/10+" Z: "+Math.floor((posZ)*10)/10,2, mc.getWindow().getScaledHeight()-2-textRenderer.fontHeight,0xFFFFFF);
        } else {
            textRenderer.drawWithShadow(matrixStack,"X: "+Math.floor((posX)*10)/10+" Y: "+Math.floor((posY)*10)/10+" Z: "+Math.floor((posZ)*10)/10,2, mc.getWindow().getScaledHeight()-4-textRenderer.fontHeight*2,0xFF0000);
            textRenderer.drawWithShadow(matrixStack,"X: "+Math.floor((posX*8)*10)/10+" Y: "+Math.floor((posY)*10)/10+" Z: "+Math.floor((posZ*8)*10)/10,2, mc.getWindow().getScaledHeight()-2-textRenderer.fontHeight,0xFFFFFF);
        }
        //matrixStack.scale(0.5f,0.5f,0.5f);
    }
}
