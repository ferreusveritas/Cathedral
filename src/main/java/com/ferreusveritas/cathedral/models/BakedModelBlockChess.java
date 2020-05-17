package com.ferreusveritas.cathedral.models;

import java.util.ArrayList;
import java.util.List;

import com.ferreusveritas.cathedral.features.chess.BlockChess;
import com.ferreusveritas.cathedral.features.chess.ChessColor;
import com.ferreusveritas.cathedral.features.chess.ChessMan;
import com.ferreusveritas.cathedral.features.chess.ChessPiece;
import com.ferreusveritas.cathedral.features.chess.ChessPieceData;
import com.ferreusveritas.cathedral.features.chess.ChessPiecesProperty;
import com.ferreusveritas.cathedral.util.UnpackedModel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.property.IExtendedBlockState;

public class BakedModelBlockChess implements IBakedModel {

	TextureAtlasSprite particleSprite;

	IBakedModel pieceModels[];

	public BakedModelBlockChess(IBakedModel[] pieceModels, TextureAtlasSprite particleSprite) {
		this.pieceModels = pieceModels;
		this.particleSprite = particleSprite;
	}

	@Override
	public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

		List<BakedQuad> quads = new ArrayList<BakedQuad>();

		if(side == null) {

			if (state != null && state.getBlock() instanceof BlockChess && state instanceof IExtendedBlockState) {
				IExtendedBlockState extendedState = ((IExtendedBlockState) state);
				ChessPieceData pieceData = extendedState.getValue(ChessPiecesProperty.CHESSPIECES);
				
				if(MinecraftForgeClient.getRenderLayer() == BlockRenderLayer.CUTOUT) {
					for(int i = 0; i < 4; i++) {
						
						ChessPiece piece = pieceData.getPiece(i);
						ChessMan man = piece.getMan();
						ChessColor color = piece.getColor();
						
						if(man != ChessMan.None) {
						
							IBakedModel model = pieceModels[(man.ordinal() - 1) + (color.ordinal() * 6)]; 

							UnpackedModel unpackedModel = new UnpackedModel(model, state, 0);
							
							double x = (((i & 1) * 2) - 1) * -0.25;
							double z = ((i & 2) - 1) * 0.25;
							unpackedModel.move(new Vec3d(x, 0.0, z));
							quads.addAll(unpackedModel.pack().get(null));
						}
					}
				}

			}

		}

		return quads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return false;
	}

	@Override
	public boolean isGui3d() {
		return false;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return true;
	}

	@Override
	public TextureAtlasSprite getParticleTexture() {
		return particleSprite;
	}

	@Override
	public ItemOverrideList getOverrides() {
		return null;
	}

}
