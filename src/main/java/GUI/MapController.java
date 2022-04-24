/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork
    Date Created:   24/04/2022
    Last Updated:   24/04/2022
 */

package GUI;

import javafx.geometry.Point2D;

public class MapController {
    /*
    // BASE MAP COORDINATES
    private final Point2D base_NRM_min = new Point2D(344, 202);
    private final Point2D base_NRM_max = new Point2D(373, 240);

    private final Point2D base_25_min = new Point2D(437, 200);
    private final Point2D base_25_max = new Point2D(475, 249);

    private final Point2D base_SW3_min = new Point2D(509, 251);
    private final Point2D base_SW3_max = new Point2D(545, 295);

    private final Point2D base_UoY_min = new Point2D(738, 336);
    private final Point2D base_UoY_max = new Point2D(776, 388);

    private final Point2D base_Charles_min = new Point2D(665, 347);
    private final Point2D base_Charles_max = new Point2D(695, 388);

    private final Point2D base_RKC_min = new Point2D(599, 347);
    private final Point2D base_RKC_max = new Point2D(626, 383);

    private final Point2D base_JBM_min = new Point2D(630, 309);
    private final Point2D base_JBM_max = new Point2D(656, 345);

    private final Point2D base_rowntree_min = new Point2D(439, 319);
    private final Point2D base_rowntree_max = new Point2D(465, 354);

    private final Point2D base_millennium_min = new Point2D(445, 384);
    private final Point2D base_millennium_max = new Point2D(474, 420);


    // Getters for all points
    public Point2D getBase_NRM_min() {
        return base_NRM_min;
    }
    public Point2D getBase_NRM_max() {
        return base_NRM_max;
    }

    public Point2D getBase_25_min() {
        return base_25_min;
    }
    public Point2D getBase_25_max() {
        return base_25_max;
    }

    public Point2D getBase_SW3_min() {
        return base_SW3_min;
    }
    public Point2D getBase_SW3_max() {
        return base_SW3_max;
    }

    public Point2D getBase_UoY_min() {
        return base_UoY_min;
    }
    public Point2D getBase_UoY_max() {
        return base_UoY_max;
    }

    public Point2D getBase_Charles_min() {
        return base_Charles_min;
    }
    public Point2D getBase_Charles_max() {
        return base_Charles_max;
    }

    public Point2D getBase_RKC_min() {
        return base_RKC_min;
    }
    public Point2D getBase_RKC_max() {
        return base_RKC_max;
    }

    public Point2D getBase_JBM_min() {
        return base_JBM_min;
    }
    public Point2D getBase_JBM_max() {
        return base_JBM_max;
    }

    public Point2D getBase_rowntree_min() {
        return base_rowntree_min;
    }
    public Point2D getBase_rowntree_max() {
        return base_rowntree_max;
    }

    public Point2D getBase_millennium_min() {
        return base_millennium_min;
    }
    public Point2D getBase_millennium_max() {
        return base_millennium_max;
    }
    */

/*
    public Object selectVenueOnMap(String currentMap, int mouseX, int mouseY) {
        Point2D mousePosition = new Point2D(mouseX, mouseY);
        Object currentItemSelected = null;

        if ((mousePosition.getX() > getBase_NRM_min().getX()) && (mousePosition.getX() < getBase_NRM_max().getX()) && (mousePosition.getY() > getBase_NRM_min().getY()) && (mousePosition.getY() < getBase_NRM_max().getY())) {
            currentItemSelected = (Object) "National Railway Museum York";
        } else if ((mousePosition.getX() > getBase_25_min().getX()) && (mousePosition.getX() < getBase_25_max().getX()) && (mousePosition.getY() > getBase_25_min().getY()) && (mousePosition.getY() < getBase_25_max().getY())) {
            currentItemSelected = (Object) "Central York 25";
        } else if ((mousePosition.getX() > getBase_SW3_min().getX()) && (mousePosition.getX() < getBase_SW3_max().getX()) && (mousePosition.getY() > getBase_SW3_min().getY()) && (mousePosition.getY() < getBase_SW3_max().getY())) {
            currentItemSelected = (Object) "South West York 3";
        } else if ((mousePosition.getX() > getBase_UoY_min().getX()) && (mousePosition.getX() < getBase_UoY_max().getX()) && (mousePosition.getY() > getBase_UoY_min().getY()) && (mousePosition.getY() < getBase_UoY_max().getY())) {
            currentItemSelected = (Object) "Uni of York 3";
        } else if ((mousePosition.getX() > getBase_Charles_min().getX()) && (mousePosition.getX() < getBase_Charles_max().getX()) && (mousePosition.getY() > getBase_Charles_min().getY()) && (mousePosition.getY() < getBase_Charles_max().getY())) {
            currentItemSelected = (Object) "Charles XII";
        } else if ((mousePosition.getX() > getBase_RKC_min().getX()) && (mousePosition.getX() < getBase_RKC_max().getX()) && (mousePosition.getY() > getBase_RKC_min().getY()) && (mousePosition.getY() < getBase_RKC_max().getY())) {
            currentItemSelected = (Object) "Roger Kirk Centre";
        } else if ((mousePosition.getX() > getBase_JBM_min().getX()) && (mousePosition.getX() < getBase_JBM_max().getX()) && (mousePosition.getY() > getBase_JBM_min().getY()) && (mousePosition.getY() < getBase_JBM_max().getY())) {
            currentItemSelected = (Object) "University of York JB Morrell Library";
        } else if ((mousePosition.getX() > getBase_rowntree_min().getX()) && (mousePosition.getX() < getBase_rowntree_max().getX()) && (mousePosition.getY() > getBase_rowntree_min().getY()) && (mousePosition.getY() < getBase_rowntree_max().getY())) {
            currentItemSelected = (Object) "Rowntree Park";
        } else if ((mousePosition.getX() > getBase_millennium_min().getX()) && (mousePosition.getX() < getBase_millennium_max().getX()) && (mousePosition.getY() > getBase_millennium_min().getY()) && (mousePosition.getY() < getBase_millennium_max().getY())) {
            currentItemSelected = (Object) "Millennium Fields";
        } else {
            currentItemSelected = (Object) "BaseMap";
        }

        return currentItemSelected;
    }
    */
}

