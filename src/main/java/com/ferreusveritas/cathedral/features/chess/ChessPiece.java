package com.ferreusveritas.cathedral.features.chess;

import net.minecraft.util.math.MathHelper;

public class ChessPiece {

	public static final ChessPiece Empty = new ChessPiece(ChessMan.None, ChessColor.White);
	
	private final ChessMan man;
	private final ChessColor color;
	
	public ChessPiece(ChessMan chessMan, ChessColor chessColor) {
		man = chessMan;
		color = chessColor;
	}
	
	public ChessMan getMan() {
		return man;
	}

	public ChessColor getColor() {
		return color;
	}
	
	public static ChessPiece fromNybble(byte data) {
		return new ChessPiece(ChessMan.values()[MathHelper.clamp(data & 7, 0, 6)], ChessColor.values()[(data >> 3) & 1]);
	}
	
	public byte toNybble() {
		return (byte) (man.ordinal() | (color.ordinal() << 3));
	}
	
}
