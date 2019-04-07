package dev.game.states;

import java.awt.Graphics;

import dev.game.game;

public abstract class State {
	
	private static State currentState = null;
	
	public static void setState(State state) {
		currentState = state;
	}
	public static State getState() {
		return currentState;
	}
	protected game Game;
	public State(game Game) {
		this.Game = Game;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
}
