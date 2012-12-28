/* Copyright © 2012 Stephen Molyneaux <@SHMolyneaux>
 * 
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */

package com.example.squarejam;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.TweenCallback;

public class SquareTouchCallback implements TweenCallback {
	private Square square;
	MyGdxGame game;
	
	public SquareTouchCallback(Square touchedSquare, MyGdxGame myGame) {
		square = touchedSquare;
		game = myGame;
	}
	
	@Override
	public void onEvent(int type, BaseTween<?> source) {
		square.touched = false;
	}

}
