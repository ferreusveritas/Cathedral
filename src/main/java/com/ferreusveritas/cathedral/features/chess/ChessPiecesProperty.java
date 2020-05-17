package com.ferreusveritas.cathedral.features.chess;

import net.minecraftforge.common.property.IUnlistedProperty;

public class ChessPiecesProperty implements IUnlistedProperty<ChessPieceData>{

	public static final ChessPiecesProperty CHESSPIECES = new ChessPiecesProperty();
	
	private final String name = "chesspieces";
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean isValid(ChessPieceData value) {
		return value != null;
	}

	@Override
	public Class<ChessPieceData> getType() {
		return ChessPieceData.class;
	}

	@Override
	public String valueToString(ChessPieceData value) {
		return value.toString();
	}

}
