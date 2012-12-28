/* Copyright © 2012 Stephen Molyneaux <@SHMolyneaux>
 * 
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
 */

package com.example.squarejam;

import com.badlogic.gdx.InputProcessor;

public class TouchProcessor implements  InputProcessor {

	private MyGdxGame mGame;
	
	public TouchProcessor(MyGdxGame game) {
		mGame = game;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		//Do nothing
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		//Do nothing
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		//Do nothing
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		mGame.touch(screenX, screenY);
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//Do nothing
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		//Do nothing
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		//Do nothing
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		//Do nothing
		return false;
	}

}
