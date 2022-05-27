/*
    Company Name:   Maptrix
    Project Name:   WiseGuide
    Authors:        Will Pitchfork
    Date Created:   24/04/2022
    Last Updated:   24/05/2022
 */

package GUI;
import javafx.geometry.Point2D;

public class MapController {
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

    private final Point2D UoY3_JBM_min = new Point2D(308, 283);
    private final Point2D UoY3_JBM_max = new Point2D(336, 321);

    private final Point2D UoY3_RKC_min = new Point2D(243, 391);
    private final Point2D UoY3_RKC_max = new Point2D(270, 428);

    private final Point2D UoY3_Charles_min = new Point2D(444, 418);
    private final Point2D UoY3_Charles_max = new Point2D(471, 456);

    private final Point2D UoY3_BlackBull_min = new Point2D(467, 138);
    private final Point2D UoY3_BlackBull_max = new Point2D(495, 175);

    private final Point2D UoY3_Cecils_min = new Point2D(728, 242);
    private final Point2D UoY3_Cecils_max = new Point2D(756, 276);

    private final Point2D UoY3_RCH_min = new Point2D(710, 362);
    private final Point2D UoY3_RCH_max = new Point2D(739, 399);

    private final Point2D UoY3_Piazza_min = new Point2D(763, 332);
    private final Point2D UoY3_Piazza_max = new Point2D(791, 360);

    private final Point2D se_efes_min = new Point2D(521, 323);
    private final Point2D se_efes_max = new Point2D(549, 361);

    private final Point2D se_rook_min = new Point2D(559, 171);
    private final Point2D se_rook_max = new Point2D(589, 205);

    private final Point2D se_waggon_min = new Point2D(596, 170);
    private final Point2D se_waggon_max = new Point2D(626, 202);

    private final Point2D se_spark_min = new Point2D(287, 53);
    private final Point2D se_spark_max = new Point2D(317, 87);

    private final Point2D se_paradiso_min = new Point2D(325, 37);
    private final Point2D se_paradiso_max = new Point2D(355, 74);

    private final Point2D cen_west_min = new Point2D(405, 285);
    private final Point2D cen_west_max = new Point2D(445, 333);

    private final Point2D cen_cen_min = new Point2D(469, 251);
    private final Point2D cen_cen_max = new Point2D(510, 296);

    private final Point2D cen_south_min = new Point2D(553, 308);
    private final Point2D cen_south_max = new Point2D(592, 359);

    private final Point2D cen_musGard_min = new Point2D(364, 164);
    private final Point2D cen_musGard_max = new Point2D(392, 201);

    private final Point2D cen_deans_min = new Point2D(520, 72);
    private final Point2D cen_deans_max = new Point2D(546, 106);

    private final Point2D cen_minster_min = new Point2D(534, 133);
    private final Point2D cen_minster_max = new Point2D(562, 170);

    private final Point2D cen_cityWalls_min = new Point2D(644, 141);
    private final Point2D cen_cityWalls_max = new Point2D(675, 179);

    private final Point2D cen_brew_min = new Point2D(447, 201);
    private final Point2D cen_brew_max = new Point2D(476, 236);

    private final Point2D cen_lucky_min = new Point2D(532, 229);
    private final Point2D cen_lucky_max = new Point2D(560, 265);

    private final Point2D cen_cats_min = new Point2D(577, 161);
    private final Point2D cen_cats_max = new Point2D(606, 196);

    private final Point2D cen_evil_min = new Point2D(503, 187);
    private final Point2D cen_evil_max = new Point2D(534, 222);

    private final Point2D cen_choc_min = new Point2D(565, 217);
    private final Point2D cen_choc_max = new Point2D(596, 253);

    private final Point2D cen_spark_min = new Point2D(644, 357);
    private final Point2D cen_spark_max = new Point2D(672, 394);

    private final Point2D cen_paradiso_min = new Point2D(679, 341);
    private final Point2D cen_paradiso_max = new Point2D(711, 378);

    private final Point2D cenSouth_spark_min = new Point2D(712, 359);
    private final Point2D cenSouth_spark_max = new Point2D(741, 397);

    private final Point2D cenSouth_paradiso_min = new Point2D(803, 319);
    private final Point2D cenSouth_paradiso_max = new Point2D(832, 357);

    private final Point2D cenSouth_cresci_min = new Point2D(539, 215);
    private final Point2D cenSouth_cresci_max = new Point2D(569, 255);

    private final Point2D cenSouth_cosy_min = new Point2D(631, 151);
    private final Point2D cenSouth_cosy_max = new Point2D(662, 190);

    private final Point2D cenSouth_hole_min = new Point2D(514, 284);
    private final Point2D cenSouth_hole_max = new Point2D(546, 325);

    private final Point2D cenSouth_deniz_min = new Point2D(525, 94);
    private final Point2D cenSouth_deniz_max = new Point2D(555, 131);

    private final Point2D cenSouth_drift_min = new Point2D(370, 238);
    private final Point2D cenSouth_drift_max = new Point2D(399, 276);

    private final Point2D cenSouth_roses_min = new Point2D(359, 293);
    private final Point2D cenSouth_roses_max = new Point2D(389, 334);

    private final Point2D cenSouth_kuda_min = new Point2D(372, 372);
    private final Point2D cenSouth_kuda_max = new Point2D(403, 411);

    private final Point2D cenWest_popworld_min = new Point2D(514, 430);
    private final Point2D cenWest_popworld_max = new Point2D(543, 469);

    private final Point2D cenWest_salvos_min = new Point2D(461, 332);
    private final Point2D cenWest_salvos_max = new Point2D(492, 374);

    private final Point2D cenWest_flares_min = new Point2D(515, 265);
    private final Point2D cenWest_flares_max = new Point2D(545, 305);

    private final Point2D cenWest_revs_min = new Point2D(656, 199);
    private final Point2D cenWest_revs_max = new Point2D(686, 236);

    private final Point2D cenWest_allBarOne_min = new Point2D(744, 183);
    private final Point2D cenWest_allBarOne_max = new Point2D(768, 220);

    private final Point2D cenWest_dusk_min = new Point2D(768, 142);
    private final Point2D cenWest_dusk_max = new Point2D(795, 180);

    private final Point2D cenCen_brew_min = new Point2D(290, 110);
    private final Point2D cenCen_brew_max = new Point2D(319, 149);

    private final Point2D cenCen_evil_min = new Point2D(503, 107);
    private final Point2D cenCen_evil_max = new Point2D(529, 143);

    private final Point2D cenCen_cats_min = new Point2D(730, 48);
    private final Point2D cenCen_cats_max = new Point2D(758, 86);

    private final Point2D cenCen_revs_min = new Point2D(331, 353);
    private final Point2D cenCen_revs_max = new Point2D(358, 391);

    private final Point2D cenCen_allBarOne_min = new Point2D(432, 340);
    private final Point2D cenCen_allBarOne_max = new Point2D(459, 369);

    private final Point2D cenCen_dusk_min = new Point2D(454, 296);
    private final Point2D cenCen_dusk_max = new Point2D(480, 331);

    private final Point2D cenCen_lucky_min = new Point2D(601, 289);
    private final Point2D cenCen_lucky_max = new Point2D(630, 326);

    private final Point2D cenCen_choc_min = new Point2D(701, 251);
    private final Point2D cenCen_choc_max = new Point2D(731, 287);

    private final Point2D cenCen_nana_min = new Point2D(710, 322);
    private final Point2D cenCen_nana_max = new Point2D(739, 359);

    private final Point2D cenCen_nana2_min = new Point2D(522, 322);
    private final Point2D cenCen_nana2_max = new Point2D(548, 359);

    private final Point2D cenCen_deniz_min = new Point2D(739, 403);
    private final Point2D cenCen_deniz_max = new Point2D(769, 441);

    private final Point2D cenCen_drift_min = new Point2D(589, 543);
    private final Point2D cenCen_drift_max = new Point2D(622, 581);

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

    public Point2D getUoY3_JBM_min() {
        return UoY3_JBM_min;
    }
    public Point2D getUoY3_JBM_max() {
        return UoY3_JBM_max;
    }

    public Point2D getUoY3_RKC_min() {
        return UoY3_RKC_min;
    }
    public Point2D getUoY3_RKC_max() {
        return UoY3_RKC_max;
    }

    public Point2D getUoY3_Charles_min() {
        return UoY3_Charles_min;
    }
    public Point2D getUoY3_Charles_max() {
        return UoY3_Charles_max;
    }

    public Point2D getUoY3_BlackBull_min() {
        return UoY3_BlackBull_min;
    }
    public Point2D getUoY3_BlackBull_max() {
        return UoY3_BlackBull_max;
    }

    public Point2D getUoY3_Cecils_min() {
        return UoY3_Cecils_min;
    }
    public Point2D getUoY3_Cecils_max() {
        return UoY3_Cecils_max;
    }

    public Point2D getUoY3_RCH_min() {
        return UoY3_RCH_min;
    }
    public Point2D getUoY3_RCH_max() {
        return UoY3_RCH_max;
    }

    public Point2D getUoY3_Piazza_min() {
        return UoY3_Piazza_min;
    }
    public Point2D getUoY3_Piazza_max() {
        return UoY3_Piazza_max;
    }

    public Point2D getSe_efes_min() {
        return se_efes_min;
    }
    public Point2D getSe_efes_max() {
        return se_efes_max;
    }

    public Point2D getSe_rook_min() {
        return se_rook_min;
    }
    public Point2D getSe_rook_max() {
        return se_rook_max;
    }

    public Point2D getSe_waggon_min() {
        return se_waggon_min;
    }
    public Point2D getSe_waggon_max() {
        return se_waggon_max;
    }

    public Point2D getSe_spark_min() {
        return se_spark_min;
    }
    public Point2D getSe_spark_max() {
        return se_spark_max;
    }

    public Point2D getSe_paradiso_min() {
        return se_paradiso_min;
    }
    public Point2D getSe_paradiso_max() {
        return se_paradiso_max;
    }

    public Point2D getCen_west_min() {
        return cen_west_min;
    }
    public Point2D getCen_west_max() {
        return cen_west_max;
    }

    public Point2D getCen_cen_min() {
        return cen_cen_min;
    }
    public Point2D getCen_cen_max() {
        return cen_cen_max;
    }

    public Point2D getCen_south_min() {
        return cen_south_min;
    }
    public Point2D getCen_south_max() {
        return cen_south_max;
    }

    public Point2D getCen_musGard_min() {
        return cen_musGard_min;
    }
    public Point2D getCen_musGard_max() {
        return cen_musGard_max;
    }

    public Point2D getCen_deans_min() {
        return cen_deans_min;
    }
    public Point2D getCen_deans_max() {
        return cen_deans_max;
    }

    public Point2D getCen_minster_min() {
        return cen_minster_min;
    }
    public Point2D getCen_minster_max() {
        return cen_minster_max;
    }

    public Point2D getCen_cityWalls_min() {
        return cen_cityWalls_min;
    }
    public Point2D getCen_cityWalls_max() {
        return cen_cityWalls_max;
    }

    public Point2D getCen_brew_min() {
        return cen_brew_min;
    }
    public Point2D getCen_brew_max() {
        return cen_brew_max;
    }

    public Point2D getCen_lucky_min() {
        return cen_lucky_min;
    }
    public Point2D getCen_lucky_max() {
        return cen_lucky_max;
    }

    public Point2D getCen_cats_min() {
        return cen_cats_min;
    }
    public Point2D getCen_cats_max() {
        return cen_cats_max;
    }

    public Point2D getCen_evil_min() {
        return cen_evil_min;
    }
    public Point2D getCen_evil_max() {
        return cen_evil_max;
    }

    public Point2D getCen_choc_min() {
        return cen_choc_min;
    }
    public Point2D getCen_choc_max() {
        return cen_choc_max;
    }

    public Point2D getCen_spark_min() {
        return cen_spark_min;
    }
    public Point2D getCen_spark_max() {
        return cen_spark_max;
    }

    public Point2D getCen_paradiso_min() {
        return cen_paradiso_min;
    }
    public Point2D getCen_paradiso_max() {
        return cen_paradiso_max;
    }

    public Point2D getCenSouth_spark_min() {
        return cenSouth_spark_min;
    }
    public Point2D getCenSouth_spark_max() {
        return cenSouth_spark_max;
    }

    public Point2D getCenSouth_paradiso_min() {
        return cenSouth_paradiso_min;
    }
    public Point2D getCenSouth_paradiso_max() {
        return cenSouth_paradiso_max;
    }

    public Point2D getCenSouth_cresci_min() {
        return cenSouth_cresci_min;
    }
    public Point2D getCenSouth_cresci_max() {
        return cenSouth_cresci_max;
    }

    public Point2D getCenSouth_cosy_min() {
        return cenSouth_cosy_min;
    }
    public Point2D getCenSouth_cosy_max() {
        return cenSouth_cosy_max;
    }

    public Point2D getCenSouth_hole_min() {
        return cenSouth_hole_min;
    }
    public Point2D getCenSouth_hole_max() {
        return cenSouth_hole_max;
    }

    public Point2D getCenSouth_deniz_min() {
        return cenSouth_deniz_min;
    }
    public Point2D getCenSouth_deniz_max() {
        return cenSouth_deniz_max;
    }

    public Point2D getCenSouth_drift_min() {
        return cenSouth_drift_min;
    }
    public Point2D getCenSouth_drift_max() {
        return cenSouth_drift_max;
    }

    public Point2D getCenSouth_roses_min() {
        return cenSouth_roses_min;
    }
    public Point2D getCenSouth_roses_max() {
        return cenSouth_roses_max;
    }

    public Point2D getCenSouth_kuda_min() {
        return cenSouth_kuda_min;
    }
    public Point2D getCenSouth_kuda_max() {
        return cenSouth_kuda_max;
    }

    public Point2D getCenWest_popworld_min() {
        return cenWest_popworld_min;
    }
    public Point2D getCenWest_popworld_max() {
        return cenWest_popworld_max;
    }

    public Point2D getCenWest_salvos_min() {
        return cenWest_salvos_min;
    }
    public Point2D getCenWest_salvos_max() {
        return cenWest_salvos_max;
    }

    public Point2D getCenWest_flares_min() {
        return cenWest_flares_min;
    }
    public Point2D getCenWest_flares_max() {
        return cenWest_flares_max;
    }

    public Point2D getCenWest_revs_min() {
        return cenWest_revs_min;
    }
    public Point2D getCenWest_revs_max() {
        return cenWest_revs_max;
    }

    public Point2D getCenWest_allBarOne_min() {
        return cenWest_allBarOne_min;
    }
    public Point2D getCenWest_allBarOne_max() {
        return cenWest_allBarOne_max;
    }

    public Point2D getCenWest_dusk_min() {
        return cenWest_dusk_min;
    }
    public Point2D getCenWest_dusk_max() {
        return cenWest_dusk_max;
    }

    public Point2D getCenCen_brew_min() {
        return cenCen_brew_min;
    }
    public Point2D getCenCen_brew_max() {
        return cenCen_brew_max;
    }

    public Point2D getCenCen_evil_min() {
        return cenCen_evil_min;
    }
    public Point2D getCenCen_evil_max() {
        return cenCen_evil_max;
    }

    public Point2D getCenCen_cats_min() {
        return cenCen_cats_min;
    }
    public Point2D getCenCen_cats_max() {
        return cenCen_cats_max;
    }

    public Point2D getCenCen_revs_min() {
        return cenCen_revs_min;
    }
    public Point2D getCenCen_revs_max() {
        return cenCen_revs_max;
    }

    public Point2D getCenCen_allBarOne_min() {
        return cenCen_allBarOne_min;
    }
    public Point2D getCenCen_allBarOne_max() {
        return cenCen_allBarOne_max;
    }

    public Point2D getCenCen_dusk_min() {
        return cenCen_dusk_min;
    }
    public Point2D getCenCen_dusk_max() {
        return cenCen_dusk_max;
    }

    public Point2D getCenCen_lucky_min() {
        return cenCen_lucky_min;
    }
    public Point2D getCenCen_lucky_max() {
        return cenCen_lucky_max;
    }

    public Point2D getCenCen_choc_min() {
        return cenCen_choc_min;
    }
    public Point2D getCenCen_choc_max() {
        return cenCen_choc_max;
    }

    public Point2D getCenCen_nana_min() {
        return cenCen_nana_min;
    }
    public Point2D getCenCen_nana_max() {
        return cenCen_nana_max;
    }

    public Point2D getCenCen_nana2_min() {
        return cenCen_nana2_min;
    }
    public Point2D getCenCen_nana2_max() {
        return cenCen_nana2_max;
    }

    public Point2D getCenCen_deniz_min() {
        return cenCen_deniz_min;
    }
    public Point2D getCenCen_deniz_max() {
        return cenCen_deniz_max;
    }

    public Point2D getCenCen_drift_min() {
        return cenCen_drift_min;
    }
    public Point2D getCenCen_drift_max() {
        return cenCen_drift_max;
    }
}

