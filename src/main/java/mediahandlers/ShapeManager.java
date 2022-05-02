package mediahandlers;

//CREATED BY ENTROPY DESIGNS FOR MAPTRIX

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class ShapeManager{

    public ShapeManager() {
        super();
    }

    public Line drawLine(int startX, int startY, int endX, int endY, Color strokeColour, int strokeWidth) {
        Line line = new Line();

        line.setStroke(strokeColour);
        line.setStrokeWidth(strokeWidth);

        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);

        return line;
    }

    public Rectangle drawRectangle(int X, int Y, int horizontalWidth, int verticalHeight, Color fill, Color strokeColour, int strokeWidth) {
        Rectangle rectangle = new Rectangle();

        if(fill == null){
            rectangle.setFill(Color.TRANSPARENT);
        }
        else{
            rectangle.setFill(fill);
        }

        rectangle.setStroke(strokeColour);
        rectangle.setStrokeWidth(strokeWidth);

        rectangle.setX(X);
        rectangle.setY(Y);
        rectangle.setWidth(horizontalWidth);
        rectangle.setHeight(verticalHeight);

        return rectangle;
    }

    public Rectangle drawCurvedRectangle(int X, int Y, int horizontalWidth, int verticalHeight,
                                    int arcWidth, int arcHeight, Color fill, Color strokeColour, int strokeWidth){
        Rectangle rectangle = new Rectangle();

        rectangle.setStroke(strokeColour);
        rectangle.setStrokeWidth(strokeWidth);

        if(fill == null){
            rectangle.setFill(Color.TRANSPARENT);
        }
        else{
            rectangle.setFill(fill);
        }

        rectangle.setX(X);
        rectangle.setY(Y);
        rectangle.setWidth(horizontalWidth);
        rectangle.setHeight(verticalHeight);

        rectangle.setArcWidth(arcWidth);
        rectangle.setArcHeight(arcHeight);

        return rectangle;
    }

    public Circle drawCircle(int centreX, int centreY, int radius, Color fill, Color strokeColour, int strokeWidth){
        Circle circle = new Circle();

        if(fill == null){
            circle.setFill(Color.TRANSPARENT);
        }
        else{
            circle.setFill(fill);
        }

        circle.setStroke(strokeColour);
        circle.setStrokeWidth(strokeWidth);

        circle.setCenterX(centreX+radius);
        circle.setCenterY(centreY+radius);
        circle.setRadius(radius);

        return circle;
    }


    public Ellipse drawEllipse(int centreX, int centreY, int radiusX, int radiusY, Color fill, Color strokeColour, int strokeWidth){
        Ellipse ellipse = new Ellipse();

        if(fill == null){
            ellipse.setFill(Color.TRANSPARENT);
        }
        else{
            ellipse.setFill(fill);
        }

        ellipse.setStroke(strokeColour);
        ellipse.setStrokeWidth(strokeWidth);

        ellipse.setCenterX(centreX);
        ellipse.setCenterY(centreY);
        ellipse.setRadiusX(radiusX);
        ellipse.setRadiusY(radiusY);

        return ellipse;
    }

    public Polygon drawTriangle(int point1X, int point1Y, int point2X, int point2Y, int point3X, int point3Y, Color fill, Color strokeColour, int strokeWidth){
        Polygon triangle = new Polygon();

        if(fill == null){
            triangle.setFill(Color.TRANSPARENT);
        }
        else{
            triangle.setFill(fill);
        }
        triangle.setStroke(strokeColour);
        triangle.setStrokeWidth(strokeWidth);

        triangle.getPoints().addAll(new Double[]{
                Double.valueOf(point1X), Double.valueOf(point1Y),
                Double.valueOf(point2X), Double.valueOf(point2Y),
                Double.valueOf(point3X), Double.valueOf(point3Y),
        });

        int smallestX = point1X;
        if(point2X < smallestX){
            smallestX = point2X;
        }
        if(point3X < smallestX){
            smallestX = point3X;
        }

        int largestX = point1X;
        if(point2X > largestX){
            largestX = point2X;
        }
        if(point3X > largestX){
            largestX = point3X;
        }

        int smallestY = point1Y;
        if(point2Y < smallestY){
            smallestY = point2Y;
        }
        if(point3Y < smallestY){
            smallestY = point3Y;
        }

        int largestY = point1Y;
        if(point2Y > largestY){
            largestY = point2Y;
        }
        if(point3Y > largestY){
            largestY = point3Y;
        }

        return triangle;
    }


}

