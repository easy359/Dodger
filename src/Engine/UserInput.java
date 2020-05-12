package Engine;

import java.awt.event.*;

public class UserInput implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {

    private static int numKeys = 255, numMouse = 5;
    private static boolean[] keys = new boolean[numKeys];
    private static boolean[] curKeys = new boolean[numKeys];
    private static boolean[] tempKeys = new boolean[numKeys];

    private static boolean[] mouse = new boolean[numMouse];
    private static boolean[] curMouse = new boolean[numMouse];
    private static boolean[] tempMouse = new boolean[numMouse];

    private static Location lastFrameLClickLoc = new Location(-100, -100);
    private static boolean justLClicked = false;
    private static Location mouseLoc = new Location(-100, -100);
    private static Location lastFrameRClickLoc = new Location(-100, -100);
    private static boolean justRClicked = false;

    private static int mouseWheelRatation;

    public void update() {
        for (int i = 0; i < numKeys; i++) {
            if (keys[i] == true) {
                if (tempKeys[i] == false && curKeys[i] == false) {
                    curKeys[i] = true;
                    tempKeys[i] = true;
                } else if (curKeys[i] == true) {
                    curKeys[i] = false;
                }
            } else if (keys[i] == false) {
                curKeys[i] = false;
                tempKeys[i] = false;
            }
        }
        for (int i = 0; i < numMouse; i++) {
            if (mouse[i] == true) {
                if (tempMouse[i] == false && curMouse[i] == false) {
                    curMouse[i] = true;
                    tempMouse[i] = true;
                } else if (curMouse[i] == true) {
                    curMouse[i] = false;
                }
            } else if (mouse[i] == false) {
                curMouse[i] = false;
                tempMouse[i] = false;
            }
        }
        if (justLClicked) {
            justLClicked = false;
        } else if (!(justLClicked)) {
            lastFrameLClickLoc.setLocation(-100, -100);
        }
        if (justRClicked) {
            justRClicked = false;
        } else if (!(justRClicked)) {
            lastFrameRClickLoc.setLocation(-100, -100);
        }
        mouseWheelRatation = 0;
    }

    public static boolean[] getKeys() {
        return keys;
    }

    public static boolean[] getCurrentKeys() {
        return curKeys;
    }

    public static boolean isKeyPressed(int keyCode) {
        return ((keyCode >= 0) && (keyCode < numKeys)) ? keys[keyCode] : false;
    }

    public static boolean isCurrentKeyPressed(int keyCode) {
        return ((keyCode >= 0) && (keyCode < numKeys)) ? curKeys[keyCode] : false;
    }

    public static boolean[] getMouse() {
        return mouse;
    }

    public static boolean[] getCurrentMouse() {
        return curMouse;
    }

    public static boolean isMousePressed(int mouseCode) {
        return ((mouseCode >= 0) && (mouseCode < numMouse)) ? mouse[mouseCode] : false;
    }

    public static boolean isCurrentMousePressed(int mouseCode) {
        return ((mouseCode >= 0) && (mouseCode < numMouse)) ? curMouse[mouseCode] : false;
    }

    public static Location getMouseLocation() {
        return mouseLoc;
    }

    public static int getMouseWheelRatation() {
        return mouseWheelRatation;
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < numKeys) {
            keys[e.getKeyCode()] = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() >= 0 && e.getKeyCode() < numKeys) {
            keys[e.getKeyCode()] = false;
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() >= 0 && e.getButton() < numMouse) {
            mouse[e.getButton()] = true;
            if (e.getButton() == Static.LEFT_CLICK) {
                lastFrameLClickLoc.setLocation(e.getX(), e.getY());
                justLClicked = true;
            } else if (e.getButton() == Static.RIGHT_CLICK) {
                lastFrameRClickLoc.setLocation(e.getX(), e.getY());
                justRClicked = true;
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getButton() >= 0 && e.getButton() < numMouse) {
            mouse[e.getButton()] = false;
        }
    }

    public void mouseMoved(MouseEvent e) {
        mouseLoc.setLocation(e.getX(), e.getY());
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelRatation += e.getWheelRotation();
    }

    public void mouseDragged(MouseEvent arg0) {
    }

    public void mouseClicked(MouseEvent arg0) {
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void keyTyped(KeyEvent arg0) {
    }

}
