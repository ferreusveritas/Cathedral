package com.ferreusveritas.stonelore.model;

import java.util.ArrayList;

import org.lwjgl.util.vector.Matrix4f;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class GroupObject
{
    public String name;
    public ArrayList<Face> faces = new ArrayList<Face>();
    public int glDrawingMode;

    public GroupObject() {
        this("");
    }

    public GroupObject(String name) {
        this(name, -1);
    }

    public GroupObject(String name, int glDrawingMode) {
        this.name = name;
        this.glDrawingMode = glDrawingMode;
    }

    @SideOnly(Side.CLIENT)
    public void render() {
    	render((IIcon)null);
    }
    
    @SideOnly(Side.CLIENT)
    public void render(IIcon icon) {
        if (faces.size() > 0) {
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawing(glDrawingMode);
            render(tessellator, icon);
            tessellator.draw();
        }
    }

    @SideOnly(Side.CLIENT)
    public void render(Tessellator tessellator) {
    	render(tessellator, null, null);
    }
    
    public void render(Tessellator tessellator, IIcon icon) {
    	render(tessellator, icon, null);
    }
    
    @SideOnly(Side.CLIENT)
    public void render(Tessellator tessellator, IIcon icon, Matrix4f transMat) {
        if (faces.size() > 0) {
            for (Face face : faces) {
                face.addFaceForRender(tessellator, icon, transMat);
            }
        }
    }
}