/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.*;

public class TurtleSoup {

    /**
     * Draw a square.
     *
     * @param turtle     the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        turtle.turn(90);
        turtle.forward(sideLength);
        //throw new RuntimeException("implement me!");
    }

    /**
     * Determine inside angles of a regular polygon.
     * <p>
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     *
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        double angle = (double) 180 * (sides - 2) / (double) sides;
        return angle;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * <p>
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     *
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        return (int) Math.round((double) 360 / ((double) 180 - angle));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * <p>
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     *
     * @param turtle     the turtle context
     * @param sides      number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        //if (sides <= 2 || sideLength <= 0)
        //throw new RuntimeException("implement me!");
        double angle = calculateRegularPolygonAngle(sides);
        turtle.turn(450 - angle);
        for (int i = 0; i < sides; i++) {
            turtle.forward(sideLength);
            turtle.turn(180 - angle);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * <p>
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360.
     * <p>
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     *
     * @param currentBearing current direction as clockwise from north
     * @param currentX       current location x-coordinate
     * @param currentY       current location y-coordinate
     * @param targetX        target point x-coordinate
     * @param targetY        target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     * must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {

        if (currentX == targetX) {
            if (currentY > targetY)
                return ((540 - currentBearing) % 360 + 360) % 360;
            else if (currentY < targetY)
                return ((360 - currentBearing) % 360 + 360) % 360;
        } else if (currentX < targetX) {
            double angle = (Math.atan((double) (targetY - currentY) / (double) (targetX - currentX))) * 180 / Math.PI;
            return ((450 - angle - currentBearing) % 360 + 360) % 360;
        } else if (currentX > targetX) {
            double angle = (Math.atan((double) (targetY - currentY) / (double) (targetX - currentX))) * 180 / Math.PI;
            return ((630 - angle - currentBearing) % 360 + 360) % 360;
        }


        return 0;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * <p>
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     *
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     * otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> list = new ArrayList<>();
        //相对y正半轴的角度
        double currentBearing = 0;
        double element;
        double angle;
        for (int i = 0; i < xCoords.size() - 1; i++) {
            if (xCoords.get(i) == xCoords.get(i + 1)) {
                if (yCoords.get(i) > yCoords.get(i + 1)) {
                    element = ((540 - currentBearing) % 360 + 360) % 360;
                    list.add(element);
                    currentBearing += element;
                    currentBearing %= 360;
                } else if (yCoords.get(i) < yCoords.get(i + 1)) {
                    element = ((360 - currentBearing) % 360 + 360) % 360;
                    list.add(element);
                    currentBearing += element;
                    currentBearing %= 360;
                }
            } else if (xCoords.get(i) < xCoords.get(i + 1)) {
                angle = (Math.atan((double) (yCoords.get(i + 1) - yCoords.get(i)) / (double) (xCoords.get(i + 1) - xCoords.get(i)))) * 180 / Math.PI;
                element = ((450 - angle - currentBearing) % 360 + 360) % 360;
                list.add(element);
                currentBearing += element;
                currentBearing %= 360;
            } else if (xCoords.get(i) > xCoords.get(i + 1)) {
                angle = (Math.atan((double) (yCoords.get(i + 1) - yCoords.get(i)) / (double) (xCoords.get(i + 1) - xCoords.get(i)))) * 180 / Math.PI;
                element = ((630 - angle - currentBearing) % 360 + 360) % 360;
                list.add(element);
                currentBearing += element;
                currentBearing %= 360;
            }

        }
        return list;

    }

    /**
     * 计算角度
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public static double angle(double x1, double y1, double x2, double y2) {
        if (x1 == x2 && y1 < y2)
            return 90;
        else if (x1 == x2 && y1 > y2)
            return 270;
        else if (y1 == y2 && x1 < x2)
            return 0;
        else if (y1 == y2 && x1 > x2)
            return 180;
        if (x1 > x2)
            return 180 + Math.atan((y2 - y1) / (x2 - x1)) * 180 / Math.PI;
        else if (x1 < x2 && y1 < y2)
            return Math.atan((y2 - y1) / (x2 - x1)) * 180 / Math.PI;
        else if (x1 < x2 && y1 > y2)
            return 360 + Math.atan((y2 - y1) / (x2 - x1)) * 180 / Math.PI;
        return 0;
    }

    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and
     * there are other algorithms too.
     *
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        Set<Point> convex = new HashSet<Point>();
        Iterator<Point> iterator = points.iterator();
        //判断要求凸集的点的个数
        int count = points.size();
        if (count == 0)
            return convex;
        else if (count == 1) {
            convex.add(iterator.next());
            return convex;
        } else if (count == 2) {
            convex.add(iterator.next());
            convex.add(iterator.next());
            return convex;
        }
        //转化为List，方便对各个点的y轴排序
        List<Point> list = new ArrayList<Point>(points);
        Collections.sort(list, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return o1.y() > o2.y() ? 1 : -1;
            }
        });
        Point O = list.get(0);
        //计算每个点相较于x正半轴的角度
        //并排序
        List<Point> list_sub = list.subList(1, count);
        Collections.sort(list_sub, new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                double angle1 = angle(O.x(), O.y(), o1.x(), o1.y());
                double angle2 = angle(O.x(), O.y(), o2.x(), o2.y());
                return angle1 < angle2 ? -1 : 1;
            }
        });

        List<Point> list_start = new ArrayList<Point>();
        list_start.add(list.get(0));
        list_start.addAll(list_sub);
        Stack<Point> stack = new Stack<Point>();
        stack.push(list_start.get(0));
        stack.push(list_start.get(1));
        Point point1 = list_start.get(0);
        Point point2 = list_start.get(1);
        int index = 2;
        double angle1;
        double angle2;
        while (index < count) {
            Point point3 = list_start.get(index);
            angle1 = angle(point1.x() - O.x(), point1.y() - O.y(), point2.x() - O.x(), point2.y() - O.y());
            angle2 = angle(point2.x() - O.x(), point2.y() - O.y(), point3.x() - O.x(), point3.y() - O.y());
            if (angle1 >= angle2) {
                stack.pop();
                point2 = stack.pop();
                point1 = stack.pop();
                stack.push(point1);
                stack.push(point2);
            } else {
                stack.push(point3);
                point1 = point2;
                point2 = point3;
                index++;
            }
        }
        while (!stack.isEmpty()) {
            convex.add(stack.pop());
        }
        return convex;
    }

    /**
     * Draw your personal, custom art.
     * <p>
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     *
     * @param turtle the turtle context
     */

    public static void drawPersonalArt(Turtle turtle) {

        int i = 0;
        for (PenColor color : PenColor.values()) {
            turtle.color(color);
            int numofangle = (i % 4 + 1) * 6;
            double interior_angle = (double) 360 / numofangle;
            for (int j = 0; j < numofangle; j++) {
                turtle.turn((double) 360 - interior_angle / 2);
                turtle.forward(i * 10 + 30);
                turtle.turn(interior_angle);
                turtle.forward(i * 10 + 30);
                turtle.turn(180 - interior_angle);
                turtle.forward(i * 10 + 30);
                turtle.turn(interior_angle);
                turtle.forward(i * 10 + 30);
                turtle.turn(180 + interior_angle);
            }
            i++;
        }
        //throw new RuntimeException("implement me!");
    }

    /**
     * Main method.
     * <p>
     * This is the method that runs when you run "java TurtleSoup".
     *
     * @param args unused
     */
    public static void main(String args[]) {
        //DrawableTurtle turtle = new DrawableTurtle();

        //drawSquare(turtle, 40);


        //八边形
        //DrawableTurtle turtle1 = new DrawableTurtle();
        //drawRegularPolygon(turtle1, 8, 60);
        //turtle1.draw();
        //System.out.println(calculateBearingToPoint(390, 0, 1, 0, 0));


        //个人作品
        DrawableTurtle turtle2 = new DrawableTurtle();
        drawPersonalArt(turtle2);
        turtle2.draw();


        // draw the window
        //turtle.draw();


    }

}
