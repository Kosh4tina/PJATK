package zad3;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface MousePressListener extends MouseListener {
    default void mouseEntered(MouseEvent e) {
    }

    default void mouseExited(MouseEvent e) {
    }

    void mousePressed(MouseEvent e);

    default void mouseReleased(MouseEvent e) {
    }

    default void mouseClicked(MouseEvent e) {
    }


}