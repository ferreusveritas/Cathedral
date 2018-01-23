package com.ferreusveritas.cathedral.model;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.model.obj.TextureCoordinate;
import net.minecraftforge.client.model.obj.Vertex;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

public class Face
{
    public Vertex[] vertices;
    public Vertex[] vertexNormals;
    public Vertex faceNormal;
    public TextureCoordinate[] textureCoordinates;
    static public float ambientLight = 0.395f;//0.4f
    static public Vector4f bakeLights[] = {
    		//(0.20000000298023224D, 1.0D, -0.699999988079071D).normalize();
    	    //(-0.20000000298023224D, 1.0D, 0.699999988079071D).normalize();
    		//new Vector4f(0.1617f, 0.8085f, -0.5659f, 0.0f),
    		//new Vector4f(-0.1617f, 0.8085f, 0.5659f, 0.0f)
    		
    		new Vector4f(0.206f, 0.89f, -0.408f, 0.0f),
    		new Vector4f(-0.206f, 0.89f, 0.408f, 0.0f)
    		
            //new Vector4f(0.0f, 1.0f, 0.0f, 0.0f),//Top
            //new Vector4f(0.0f, -0.5f, 0.0f, 0.0f),//Bottom
            //new Vector4f(-0.6f, 0.0f, 0.0f, 0.0f),//West
            //new Vector4f(0.6f, 0.0f, 0.0f, 0.0f),//East
            //new Vector4f(0.0f, 0.0f, -0.8f, 0.0f),//North
            //new Vector4f(0.0f, 0.0f, 0.8f, 0.0f)//South
    };

    static public boolean bakeLighting = false;
    
    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator) {
        addFaceForRender(tessellator, 0.0005F, null, null);
    }
    
    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator, IIcon icon) {
        addFaceForRender(tessellator, 0.0005F, icon, null);
    }
    
    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator, IIcon icon, Matrix4f transMat) {
        addFaceForRender(tessellator, 0.0005F, icon, transMat);
    }

    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator, float textureOffset) {
    	addFaceForRender(tessellator, textureOffset, null, null);
    }
    
    public float clamp(float value, float min, float max) {
    	return value < min ? min : value > max ? max : value; 
    }
    
    @SideOnly(Side.CLIENT)
    public void addFaceForRender(Tessellator tessellator, float textureOffset, IIcon icon, Matrix4f transMat) {
    		
        if (faceNormal == null) {
            faceNormal = this.calculateFaceNormal();
        }

        Vector4f transNormal = new Vector4f(faceNormal.x, faceNormal.y, faceNormal.z, 0.0f);
        
        if(transMat != null){
    		Matrix4f.transform(transMat, transNormal, transNormal);
        }
        
        tessellator.setNormal(transNormal.x, transNormal.y, transNormal.z);

        //Bake lighting values
        if(bakeLighting){
        	float val = ambientLight;//Ambient lighting value
        	for(Vector4f light: bakeLights){
        		val += clamp(Vector4f.dot(transNormal, light), 0.0f, 1.0f);
        	}
        	val = clamp(val, 0.0f, 1.0f);
        	tessellator.setColorOpaque_F(val, val, val);
        }
        
        float averageU = 0F;
        float averageV = 0F;

        if ((textureCoordinates != null) && (textureCoordinates.length > 0)) {
            for (int i = 0; i < textureCoordinates.length; ++i) {
                averageU += textureCoordinates[i].u;
                averageV += textureCoordinates[i].v;
            }

            averageU = averageU / textureCoordinates.length;
            averageV = averageV / textureCoordinates.length;
        }

        float offsetU, offsetV;
        float realU, realV;
    	Vector4f pointVec = new Vector4f();
        
        for (int i = 0; i < vertices.length; ++i) {

        	pointVec.set(vertices[i].x, vertices[i].y, vertices[i].z, 1.0f);

        	if(transMat != null){
        		Matrix4f.transform(transMat, pointVec, pointVec);
        	}
        	
            if ((textureCoordinates != null) && (textureCoordinates.length > 0)) {
                offsetU = textureOffset;
                offsetV = textureOffset;

                if (textureCoordinates[i].u > averageU) {
                    offsetU = -offsetU;
                }
                if (textureCoordinates[i].v > averageV) {
                    offsetV = -offsetV;
                }
                                
            	realU = textureCoordinates[i].u + offsetU;
        		realV = textureCoordinates[i].v + offsetV;
            	
            	if(icon != null){
            		realU = icon.getInterpolatedU(realU * 16.0f);
            		realV = icon.getInterpolatedV(realV * 16.0f);
            	}
            	
                tessellator.addVertexWithUV(pointVec.x, pointVec.y, pointVec.z, realU, realV);
            }
            else {
                tessellator.addVertex(pointVec.x, pointVec.y, pointVec.z);
            }
        }
    }

    public Vertex calculateFaceNormal()
    {
        Vec3 v1 = Vec3.createVectorHelper(vertices[1].x - vertices[0].x, vertices[1].y - vertices[0].y, vertices[1].z - vertices[0].z);
        Vec3 v2 = Vec3.createVectorHelper(vertices[2].x - vertices[0].x, vertices[2].y - vertices[0].y, vertices[2].z - vertices[0].z);
        Vec3 normalVector = null;

        normalVector = v1.crossProduct(v2).normalize();

        return new Vertex((float) normalVector.xCoord, (float) normalVector.yCoord, (float) normalVector.zCoord);
    }
}