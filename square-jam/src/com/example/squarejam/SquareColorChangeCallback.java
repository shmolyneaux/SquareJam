/* Copyright © 2012 Stephen Molyneaux <@SHMolyneaux>
 * 
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */

package com.example.squarejam;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

public class SquareColorChangeCallback implements TweenCallback {
	private Square block;
	private MyGdxGame game;
	
	private int color = -1;
	
	public SquareColorChangeCallback (Square block, MyGdxGame game) {
		this.block = block;
		this.game = game;
	}
	
	public SquareColorChangeCallback (Square block, MyGdxGame game, int c) {
		this.block = block;
		this.game = game;
		this.color = c;
	}
	
	@Override
	public void onEvent(int type, BaseTween<?> source) {
		if (color != -1) {
			game.changeColor(block, color);
		}
		
		else {
			game.changeColor(block);
		}
	}
}
