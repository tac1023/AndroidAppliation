package edu.unh.cs.cs619.bulletzone.repository;

import edu.unh.cs.cs619.bulletzone.model.Coast;
import edu.unh.cs.cs619.bulletzone.model.DebrisField;
import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.FieldHolder;
import edu.unh.cs.cs619.bulletzone.model.Game;
import edu.unh.cs.cs619.bulletzone.model.Hill;
import edu.unh.cs.cs619.bulletzone.model.RockAndDirt;
import edu.unh.cs.cs619.bulletzone.model.Wall;
import edu.unh.cs.cs619.bulletzone.model.Water;
import edu.unh.cs.cs619.bulletzone.model.WaterCurrent;

import static edu.unh.cs.cs619.bulletzone.model.Direction.Above;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Below;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Down;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Left;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Right;
import static edu.unh.cs.cs619.bulletzone.model.Direction.Up;

/**
 * Handles defining the map and loading the FieldHolder grid
 * for the game.
 *
 * @author Tyler Currier
 * @version 2.0
 * @since 5/2/18
 */

final class MapLoader {

    /**
     * Field dimensions
     */
    private static final int FIELD_DIM = 16;

    /**
     * Load various map items into the game map directly through the
     * Game parameter. This is the starting map for any game.
     *
     * @param game reference to Game to load map
     */
    static void loadMap(Game game) {

        clearMap(game);
        //Above Ground

        //Row 1:
        game.getHolderGrid().get(0).setFieldEntity(new Hill());
        game.getHolderGrid().get(1).setFieldEntity(new Hill());
        game.getHolderGrid().get(4).setFieldEntity(new Coast());
        game.getHolderGrid().get(5).setFieldEntity(new Coast());
        game.getHolderGrid().get(6).setFieldEntity(new Wall());
        game.getHolderGrid().get(7).setFieldEntity(new Coast());
        game.getHolderGrid().get(8).setFieldEntity(new Coast());
        game.getHolderGrid().get(9).setFieldEntity(new Coast());
        game.getHolderGrid().get(10).setFieldEntity(new Coast());
        game.getHolderGrid().get(11).setFieldEntity(new Water());
        game.getHolderGrid().get(12).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(13).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(14).setFieldEntity(new Water());
        game.getHolderGrid().get(15).setFieldEntity(new Water());

        //Row 2:
        game.getHolderGrid().get(16).setFieldEntity(new Hill());
        game.getHolderGrid().get(17).setFieldEntity(new Hill());
        game.getHolderGrid().get(22).setFieldEntity(new Wall(1150, 22));
        game.getHolderGrid().get(26).setFieldEntity(new Coast());
        game.getHolderGrid().get(27).setFieldEntity(new Water());
        game.getHolderGrid().get(28).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(29).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(30).setFieldEntity(new WaterCurrent(Right));
        game.getHolderGrid().get(31).setFieldEntity(new WaterCurrent(Right));

        //Row 3:
        game.getHolderGrid().get(32).setFieldEntity(new Hill());
        game.getHolderGrid().get(33).setFieldEntity(new Hill());
        game.getHolderGrid().get(35).setFieldEntity(new DebrisField(false));
        game.getHolderGrid().get(36).setFieldEntity(new DebrisField(false));
        game.getHolderGrid().get(38).setFieldEntity(new Wall());
        game.getHolderGrid().get(42).setFieldEntity(new Coast());
        game.getHolderGrid().get(43).setFieldEntity(new Water());
        game.getHolderGrid().get(44).setFieldEntity(new Water());
        game.getHolderGrid().get(45).setFieldEntity(new Water());
        game.getHolderGrid().get(46).setFieldEntity(new Water());
        game.getHolderGrid().get(47).setFieldEntity(new Coast());

        //Row 4:
        game.getHolderGrid().get(48).setFieldEntity(new Hill());
        game.getHolderGrid().get(49).setFieldEntity(new Hill());
        game.getHolderGrid().get(51).setFieldEntity(new DebrisField(false));
        game.getHolderGrid().get(52).setFieldEntity(new DebrisField(false));
        game.getHolderGrid().get(54).setFieldEntity(new Wall(1150, 54));
        game.getHolderGrid().get(58).setFieldEntity(new Coast());
        game.getHolderGrid().get(59).setFieldEntity(new Water());
        game.getHolderGrid().get(60).setFieldEntity(new Water());
        game.getHolderGrid().get(61).setFieldEntity(new Water());
        game.getHolderGrid().get(62).setFieldEntity(new Water());
        game.getHolderGrid().get(63).setFieldEntity(new Coast());

        //Row 5:
        game.getHolderGrid().get(70).setFieldEntity(new Wall());
        game.getHolderGrid().get(74).setFieldEntity(new Coast());
        game.getHolderGrid().get(75).setFieldEntity(new Coast());
        game.getHolderGrid().get(76).setFieldEntity(new Coast());
        game.getHolderGrid().get(77).setFieldEntity(new Coast());
        game.getHolderGrid().get(78).setFieldEntity(new Coast());
        game.getHolderGrid().get(79).setFieldEntity(new Coast());

        //Row 6:
        game.getHolderGrid().get(86).setFieldEntity(new Wall(1150, 86));

        //Row 7:
        game.getHolderGrid().get(100).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(101).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(102).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(103).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(104).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(105).setFieldEntity(new DebrisField(true));

        //Row 8:
        game.getHolderGrid().get(116).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(117).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(118).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(119).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(120).setFieldEntity(new Hill());
        game.getHolderGrid().get(121).setFieldEntity(new DebrisField(true));

        //Row 9:
        game.getHolderGrid().get(128).setFieldEntity(new Wall());
        game.getHolderGrid().get(129).setFieldEntity(new Wall(1150, 129));
        game.getHolderGrid().get(130).setFieldEntity(new Wall());
        game.getHolderGrid().get(131).setFieldEntity(new Wall(1150, 131));
        game.getHolderGrid().get(132).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(133).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(134).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(135).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(136).setFieldEntity(new Hill());
        game.getHolderGrid().get(137).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(138).setFieldEntity(new Wall());
        game.getHolderGrid().get(139).setFieldEntity(new Wall(1150, 139));
        game.getHolderGrid().get(140).setFieldEntity(new Wall());
        game.getHolderGrid().get(141).setFieldEntity(new Wall(1150, 141));
        game.getHolderGrid().get(142).setFieldEntity(new Wall());
        game.getHolderGrid().get(143).setFieldEntity(new Wall(1150, 143));

        //Row 10:
        game.getHolderGrid().get(148).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(149).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(150).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(151).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(152).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(153).setFieldEntity(new DebrisField(true));
        game.getHolderGrid().get(154).setFieldEntity(new Water());
        game.getHolderGrid().get(155).setFieldEntity(new Water());

        //Row 11:
        game.getHolderGrid().get(166).setFieldEntity(new Wall());
        game.getHolderGrid().get(169).setFieldEntity(new Coast());
        game.getHolderGrid().get(170).setFieldEntity(new Coast());
        game.getHolderGrid().get(171).setFieldEntity(new Coast());
        game.getHolderGrid().get(172).setFieldEntity(new Coast());
        game.getHolderGrid().get(173).setFieldEntity(new Coast());
        game.getHolderGrid().get(174).setFieldEntity(new Coast());
        game.getHolderGrid().get(175).setFieldEntity(new Coast());

        //Row 12:
        game.getHolderGrid().get(182).setFieldEntity(new Wall(1150, 182));
        game.getHolderGrid().get(185).setFieldEntity(new Water());
        game.getHolderGrid().get(186).setFieldEntity(new Coast());
        game.getHolderGrid().get(187).setFieldEntity(new Water());
        game.getHolderGrid().get(188).setFieldEntity(new Water());
        game.getHolderGrid().get(189).setFieldEntity(new Water());
        game.getHolderGrid().get(190).setFieldEntity(new Water());
        game.getHolderGrid().get(191).setFieldEntity(new Coast());

        //Row 13:
        game.getHolderGrid().get(198).setFieldEntity(new Wall());
        game.getHolderGrid().get(199).setFieldEntity(new Coast());
        game.getHolderGrid().get(200).setFieldEntity(new Water());
        game.getHolderGrid().get(201).setFieldEntity(new Water());
        game.getHolderGrid().get(202).setFieldEntity(new Coast());
        game.getHolderGrid().get(203).setFieldEntity(new Water());
        game.getHolderGrid().get(204).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(205).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(206).setFieldEntity(new Water());
        game.getHolderGrid().get(207).setFieldEntity(new Coast());

        //Row 14:
        game.getHolderGrid().get(208).setFieldEntity(new Hill());
        game.getHolderGrid().get(214).setFieldEntity(new Wall(1150, 214));
        game.getHolderGrid().get(215).setFieldEntity(new Coast());
        game.getHolderGrid().get(216).setFieldEntity(new Water());
        game.getHolderGrid().get(217).setFieldEntity(new Water());
        game.getHolderGrid().get(218).setFieldEntity(new Water());
        game.getHolderGrid().get(219).setFieldEntity(new Water());
        game.getHolderGrid().get(220).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(221).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(222).setFieldEntity(new Water());
        game.getHolderGrid().get(223).setFieldEntity(new Coast());

        //Row 15:
        game.getHolderGrid().get(224).setFieldEntity(new Hill());
        game.getHolderGrid().get(225).setFieldEntity(new Hill());
        game.getHolderGrid().get(226).setFieldEntity(new Hill());
        game.getHolderGrid().get(228).setFieldEntity(new Coast());
        game.getHolderGrid().get(229).setFieldEntity(new Coast());
        game.getHolderGrid().get(230).setFieldEntity(new Coast());
        game.getHolderGrid().get(231).setFieldEntity(new Coast());
        game.getHolderGrid().get(232).setFieldEntity(new Water());
        game.getHolderGrid().get(233).setFieldEntity(new Water());
        game.getHolderGrid().get(234).setFieldEntity(new Water());
        game.getHolderGrid().get(235).setFieldEntity(new Water());
        game.getHolderGrid().get(236).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(237).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(238).setFieldEntity(new Water());
        game.getHolderGrid().get(239).setFieldEntity(new Coast());

        //Row 16:
        game.getHolderGrid().get(240).setFieldEntity(new Hill());
        game.getHolderGrid().get(241).setFieldEntity(new Hill());
        game.getHolderGrid().get(242).setFieldEntity(new Hill());
        game.getHolderGrid().get(244).setFieldEntity(new Water());
        game.getHolderGrid().get(245).setFieldEntity(new Water());
        game.getHolderGrid().get(246).setFieldEntity(new Water());
        game.getHolderGrid().get(247).setFieldEntity(new WaterCurrent(Left));
        game.getHolderGrid().get(248).setFieldEntity(new WaterCurrent(Left));
        game.getHolderGrid().get(249).setFieldEntity(new WaterCurrent(Left));
        game.getHolderGrid().get(250).setFieldEntity(new WaterCurrent(Left));
        game.getHolderGrid().get(251).setFieldEntity(new WaterCurrent(Left));
        game.getHolderGrid().get(252).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(253).setFieldEntity(new WaterCurrent(Up));
        game.getHolderGrid().get(254).setFieldEntity(new Water());
        game.getHolderGrid().get(255).setFieldEntity(new Coast());


        //Below Ground

        //Row 1:
        game.getSubHolderGrid().get(0).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(1).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(2).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(3).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(4).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(5).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(6).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(7).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(8).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(9).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(10).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(11).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(12).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(13).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(14).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(15).setFieldEntity(new RockAndDirt(4200));

        //Row 2:
        game.getSubHolderGrid().get(16).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(17).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(18).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(19).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(20).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(21).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(22).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(23).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(24).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(25).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(26).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(27).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(28).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(29).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(30).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(31).setFieldEntity(new RockAndDirt(4200));

        //Row 3:
        game.getSubHolderGrid().get(32).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(33).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(34).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(35).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(36).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(37).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(38).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(39).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(40).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(41).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(42).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(43).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(44).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(45).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(46).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(47).setFieldEntity(new RockAndDirt(4200));

        //Row 4:
        game.getSubHolderGrid().get(48).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(49).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(50).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(51).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(52).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(53).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(54).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(55).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(56).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(57).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(58).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(59).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(60).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(61).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(62).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(63).setFieldEntity(new RockAndDirt(4200));

        //Row 5:
        game.getSubHolderGrid().get(64).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(65).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(66).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(67).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(68).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(69).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(70).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(71).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(72).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(73).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(74).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(75).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(76).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(77).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(78).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(79).setFieldEntity(new RockAndDirt(4200));

        //Row 6:
        game.getSubHolderGrid().get(80).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(81).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(82).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(83).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(84).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(85).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(86).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(87).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(88).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(89).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(90).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(91).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(92).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(93).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(94).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(95).setFieldEntity(new RockAndDirt(4000));

        //Row 7:
        game.getSubHolderGrid().get(96).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(97).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(98).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(99).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(100).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(101).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(102).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(103).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(104).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(105).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(106).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(107).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(108).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(109).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(110).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(111).setFieldEntity(new RockAndDirt(4000));

        //Row 8:
        game.getSubHolderGrid().get(112).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(113).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(114).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(115).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(116).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(117).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(118).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(119).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(120).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(121).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(122).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(123).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(124).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(125).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(126).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(127).setFieldEntity(new RockAndDirt(4000));

        //Row 9:
        game.getSubHolderGrid().get(128).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(129).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(130).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(131).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(132).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(133).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(134).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(135).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(136).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(137).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(138).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(139).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(140).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(141).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(142).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(143).setFieldEntity(new RockAndDirt(4200));

        //Row 10:
        game.getSubHolderGrid().get(144).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(145).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(146).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(147).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(148).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(149).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(150).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(151).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(152).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(153).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(154).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(155).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(156).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(157).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(158).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(159).setFieldEntity(new RockAndDirt(3));

        //Row 11:
        game.getSubHolderGrid().get(160).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(161).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(162).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(163).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(164).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(165).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(166).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(167).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(168).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(169).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(170).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(171).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(172).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(173).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(174).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(175).setFieldEntity(new RockAndDirt(4200));

        //Row 12:
        game.getSubHolderGrid().get(176).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(177).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(178).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(179).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(180).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(181).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(182).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(183).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(184).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(185).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(186).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(187).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(188).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(189).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(190).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(191).setFieldEntity(new RockAndDirt(4200));

        //Row 13:
        game.getSubHolderGrid().get(192).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(193).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(194).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(195).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(196).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(197).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(198).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(199).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(200).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(201).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(202).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(203).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(204).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(205).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(206).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(207).setFieldEntity(new RockAndDirt(4000));

        //Row 14:
        game.getSubHolderGrid().get(208).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(209).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(210).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(211).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(212).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(213).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(214).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(215).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(216).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(217).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(218).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(219).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(220).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(221).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(222).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(223).setFieldEntity(new RockAndDirt(4200));

        //Row 15:
        game.getSubHolderGrid().get(224).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(225).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(226).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(227).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(228).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(229).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(230).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(231).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(232).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(233).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(234).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(235).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(236).setFieldEntity(new RockAndDirt(3));
        game.getSubHolderGrid().get(237).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(238).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(239).setFieldEntity(new RockAndDirt(4200));

        //Row 16:
        game.getSubHolderGrid().get(240).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(241).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(242).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(243).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(244).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(245).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(246).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(247).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(248).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(249).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(250).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(251).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(252).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(253).setFieldEntity(new RockAndDirt(4200));
        game.getSubHolderGrid().get(254).setFieldEntity(new RockAndDirt(4000));
        game.getSubHolderGrid().get(255).setFieldEntity(new RockAndDirt(4000));

        setParents(game);
    }

    /**
     * Clear the map before building a new one
     *
     * @param game Game whose map to clear
     */
    private static void clearMap(Game game)
    {
        for(int i = 0; i < 256; i++)
        {
            game.getHolderGrid().get(i).clearField();
            game.getSubHolderGrid().get(i).clearField();
        }
    }

    /**
     * Set the parents of all the entities in the map
     *
     * @param game Game whose entities to parent-match
     */
    private static void setParents(Game game)
    {
        for(int i = 0; i < 256; i++)
        {
            if(game.getHolderGrid().get(i).isPresent())
            {
                game.getHolderGrid().get(i).getEntity().setParent(game.getHolderGrid().get(i));
            }
            if(game.getSubHolderGrid().get(i).isPresent())
            {
                game.getSubHolderGrid().get(i).getEntity().setParent(game.getSubHolderGrid().get(i));
            }
        }
    }

    /**
     * Creates a new Field Holder grid given a game.
     *
     * @param game game in which to load field holder grid
     */
    static void createFieldHolderGrid(Game game) {
        synchronized (Monitor.getInstance()) {
            game.getHolderGrid().clear(); //clear above ground
            game.getSubHolderGrid().clear(); //clear below ground
            for (int i = 0; i < FIELD_DIM * FIELD_DIM; i++) {
                game.getHolderGrid().add(new FieldHolder());
                game.getSubHolderGrid().add(new FieldHolder());
            }

            FieldHolder targetHolder;
            FieldHolder rightHolder;
            FieldHolder downHolder;

            FieldHolder subTargetHolder;
            FieldHolder subRightHolder;
            FieldHolder subDownHolder;

            // Build connections
            for (int i = 0; i < FIELD_DIM; i++) {
                for (int j = 0; j < FIELD_DIM; j++) {
                    targetHolder = game.getHolderGrid().get(i * FIELD_DIM + j);
                    subTargetHolder = game.getSubHolderGrid().get(i * FIELD_DIM +j);

                    rightHolder = game.getHolderGrid().get(i * FIELD_DIM
                            + ((j + 1) % FIELD_DIM));
                    subRightHolder = game.getSubHolderGrid().get(i * FIELD_DIM
                            + ((j + 1) % FIELD_DIM));

                    downHolder = game.getHolderGrid().get(((i + 1) % FIELD_DIM)
                            * FIELD_DIM + j);
                    subDownHolder = game.getSubHolderGrid().get(((i + 1) % FIELD_DIM)
                            * FIELD_DIM + j);

                    //pair right above ground
                    targetHolder.addNeighbor(Right, rightHolder);
                    rightHolder.addNeighbor(Direction.Left, targetHolder);

                    //pair right below ground
                    subTargetHolder.addNeighbor(Right, subRightHolder);
                    subRightHolder.addNeighbor(Left, subTargetHolder);

                    //pair down above ground
                    targetHolder.addNeighbor(Direction.Down, downHolder);
                    downHolder.addNeighbor(Up, targetHolder);

                    //pair down below ground
                    subTargetHolder.addNeighbor(Down, subDownHolder);
                    subDownHolder.addNeighbor(Up, subTargetHolder);

                    //pair above and below for target
                    targetHolder.addNeighbor(Below, subTargetHolder);
                    subTargetHolder.addNeighbor(Above, targetHolder);
                }
            }
        }
    }
}
