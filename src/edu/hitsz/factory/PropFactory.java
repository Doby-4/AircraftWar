package edu.hitsz.factory;

import edu.hitsz.props.AbstractProps;

public interface PropFactory {
    AbstractProps createProp(int locationX, int locationY, int speedX, int speedY);
}
