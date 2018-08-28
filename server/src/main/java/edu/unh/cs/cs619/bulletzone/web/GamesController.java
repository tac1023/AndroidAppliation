package edu.unh.cs.cs619.bulletzone.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import javax.servlet.http.HttpServletRequest;

import edu.unh.cs.cs619.bulletzone.model.Direction;
import edu.unh.cs.cs619.bulletzone.model.IllegalTransitionException;
import edu.unh.cs.cs619.bulletzone.model.LimitExceededException;
import edu.unh.cs.cs619.bulletzone.model.SoldierDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.model.Tank;
import edu.unh.cs.cs619.bulletzone.model.TankDoesNotExistException;
import edu.unh.cs.cs619.bulletzone.repository.GameRepository;
import edu.unh.cs.cs619.bulletzone.tank_action.Action;
import edu.unh.cs.cs619.bulletzone.util.BooleanWrapper;
import edu.unh.cs.cs619.bulletzone.util.GridWrapper;
import edu.unh.cs.cs619.bulletzone.util.LongWrapper;

/**
 * This class returns the truth value of the requested action by calling
 * GameRepository to execute the move and getting a truth value back.
 * This response will then be sent to the Client.
 *
 * @author ???, Tyler Currier, Jason Vettese
 * @version 1.1
 * @since 4/17/18
 */
@RestController
@RequestMapping(value = "/games")
class GamesController {

    private static final Logger log = LoggerFactory.getLogger(GamesController.class);

    private final GameRepository gameRepository;
    private final Action playerAction;

    /**
     * Constructor for GamesController that initializes a GameRepository
     *
     * @param gameRepository Game repository to track the game.
     */
    @Autowired
    public GamesController(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.playerAction = new Action();
    }

    /**
     * Try adding a new tank to the game.
     *
     * @param request Request from the client
     * @return Tank ID of new tank if successful, else null
     */
    @RequestMapping(method = RequestMethod.POST, value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    ResponseEntity<LongWrapper> join(HttpServletRequest request) {
        Tank tank;
        try {
            tank = gameRepository.join(request.getRemoteAddr());
            log.info("Player joined: tankId={} IP={}", tank.getId(), request.getRemoteAddr());

            return new ResponseEntity<LongWrapper>(
                    new LongWrapper(tank.getId()),
                    HttpStatus.CREATED
            );
        } catch (RestClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Web browser compatible version of grid
     *
     * @return latest update of grid
     */
    @RequestMapping(method = RequestMethod.GET, value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResponseEntity<GridWrapper> grid() {

        return new ResponseEntity<GridWrapper>(new GridWrapper(gameRepository.getGrid(-1), gameRepository.getTankGrid(-1), true,
                0, 0),
                HttpStatus.ACCEPTED);
    }

    /**
     * Return the latest version of the grid when requested
     *
     * @return latest update of grid.
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{tankId}/grid", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public
    @ResponseBody
    ResponseEntity<GridWrapper> grid(@PathVariable long tankId, HttpServletRequest request) {
        gameRepository.resetTimer(request.getRemoteAddr());

        boolean alive;
        try {
            alive = gameRepository.getIsAlive(tankId);
        }
        catch(TankDoesNotExistException e)
        {
            alive = false;
        }

        int tankHealth;
        int soldierHealth;

        try {
            tankHealth = gameRepository.getTankHealth(tankId);
            soldierHealth = gameRepository.getSoldierHealth(tankId);
        }
        catch(TankDoesNotExistException e)
        {
            tankHealth = 0;
            soldierHealth = 0;
        }
        return new ResponseEntity<GridWrapper>(new GridWrapper(gameRepository.getGrid(tankId), gameRepository.getTankGrid(tankId), alive,
                tankHealth, soldierHealth),
                HttpStatus.ACCEPTED);
    }

    /**
     * Try to turn the tank owned by the client who requested this turn
     *
     * @param tankId ID of tank to turn
     * @param direction Direction to which to turn tank.
     * @return True if successful, False if not
     * @throws TankDoesNotExistException There is no tank with this tankId
     * @throws LimitExceededException Not allowed to turn yet
     * @throws IllegalTransitionException Not allowed to execute this turn (180 degrees?)
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{tankId}/turn/{direction}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BooleanWrapper> turn(@PathVariable long tankId, @PathVariable byte direction)
            throws TankDoesNotExistException, SoldierDoesNotExistException,
                LimitExceededException, IllegalTransitionException {
        return new ResponseEntity<BooleanWrapper>(
                new BooleanWrapper(playerAction.turn(tankId, Direction.fromByte(direction))),
                HttpStatus.ACCEPTED
        ); //EDITED TO USE playerAction INSTEAD OF gameRepository
    }

    /**
     * Try to move the tank owned by the client who requested this move in the indicated
     * direction
     *
     * @param tankId ID of tank to move
     * @param direction Direction in which to move tank
     * @return True if successful, False if not
     * @throws TankDoesNotExistException There is no tank with this tankId
     * @throws LimitExceededException Not allowed to move yet
     * @throws IllegalTransitionException Not allowed to move in this direction
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{tankId}/move/{direction}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BooleanWrapper> move(@PathVariable long tankId, @PathVariable byte direction)
            throws TankDoesNotExistException, SoldierDoesNotExistException,
                LimitExceededException, IllegalTransitionException {
        return new ResponseEntity<BooleanWrapper>(
                new BooleanWrapper(playerAction.move(tankId, Direction.fromByte(direction))),
                HttpStatus.ACCEPTED
        ); //EDITED TO USE playerAction INSTEAD OF gameRepository
    }

    /**
     * Try to fire a bullet of the indicated type from the tank owned by the client who
     * requested this fire
     *
     * @param tankId ID of tank from which to fire
     * @param bulletType Type of bullet to fire
     * @return True if successful, False if not
     * @throws TankDoesNotExistException There is no tank with this tankId
     * @throws LimitExceededException Not allowed to fire bullet yet.
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{tankId}/fire/{bulletType}/{direction}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BooleanWrapper> fire(@PathVariable long tankId, @PathVariable int bulletType, @PathVariable int direction)
            throws TankDoesNotExistException, SoldierDoesNotExistException, LimitExceededException {
        return new ResponseEntity<BooleanWrapper>(
                new BooleanWrapper(playerAction.fire(tankId, bulletType, direction)),
                HttpStatus.ACCEPTED
        ); //EDITED TO USE playerAction INSTEAD OF gameRepository
    }


    @RequestMapping(method = RequestMethod.PUT, value = "{tankId}/fireMissile/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BooleanWrapper> fireMisslie(@PathVariable long tankId)
        throws TankDoesNotExistException, SoldierDoesNotExistException, LimitExceededException {
        return new ResponseEntity<BooleanWrapper>(
                new BooleanWrapper(playerAction.fireMissile(tankId)),
                HttpStatus.ACCEPTED
        );
    }

    /**
     * Handles the ejection of a soldier from a specified tankId.
     *
     * @param tankId ID of tank from which to eject
     * @return success or failure
     * @throws TankDoesNotExistException There is no such tank
     * @throws LimitExceededException Not allowed to eject
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{tankId}/eject/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BooleanWrapper> eject(@PathVariable long tankId )
            throws TankDoesNotExistException, LimitExceededException {

        return new ResponseEntity<BooleanWrapper>(
                    new BooleanWrapper(playerAction.eject(tankId)), HttpStatus.ACCEPTED);
    }

    /**
     * Handles the creation of a tunneler from the specified tankId.
     *
     * @param tankId ID of tank to transform into tunneler
     * @return success or failure
     * @throws TankDoesNotExistException No such tank
     * @throws LimitExceededException Can't transform
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{tankId}/tunneler/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BooleanWrapper> toTunneler(@PathVariable long tankId )
            throws TankDoesNotExistException, LimitExceededException {

        return new ResponseEntity<BooleanWrapper>(
                new BooleanWrapper(playerAction.toTunneler(tankId)), HttpStatus.ACCEPTED);
    }

    /**
     * Handles the creation of a ship from the specified tankId.
     *
     * @param tankId ID of tank to transform int ship
     * @return success or failure
     * @throws TankDoesNotExistException No such tank
     * @throws LimitExceededException Can't transform
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{tankId}/ship/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BooleanWrapper> toShip(@PathVariable long tankId )
            throws TankDoesNotExistException, LimitExceededException {


        return new ResponseEntity<BooleanWrapper>(
                new BooleanWrapper(playerAction.toShip(tankId)), HttpStatus.ACCEPTED);
    }

    /**
     * Move up and down between the levels
     *
     * @param tankId ID of tank to drill
     * @return success or failure
     * @throws TankDoesNotExistException NO such tank
     * @throws LimitExceededException Can't drill
     * @throws IllegalTransitionException Can't drill there
     */
    @RequestMapping(method = RequestMethod.PUT, value = "{tankId}/drill/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<BooleanWrapper> drill(@PathVariable long tankId)
            throws TankDoesNotExistException, LimitExceededException, IllegalTransitionException {

        return new ResponseEntity<BooleanWrapper>(
                new BooleanWrapper(playerAction.drill(tankId)), HttpStatus.ACCEPTED);
    }


    /**
     * Destroy the tank of the client who requested to leave
     *
     * @param tankId ID of tank to destroy
     * @return True if successful, False if not
     * @throws TankDoesNotExistException There is no tank with this tankId
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "{tankId}/leave", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    HttpStatus leave(@PathVariable long tankId)
            throws TankDoesNotExistException {
        //System.out.println("Games Controller leave() called, tank ID: "+tankId);
        try {
            gameRepository.leave(tankId);
            return HttpStatus.ACCEPTED;
        }
        catch(TankDoesNotExistException e)
        {
            return HttpStatus.BAD_REQUEST;
        }
    }

    /**
     * There has been a request that isn't recognized by the server
     *
     * @param e The Exception thrown by this request
     * @return The message of this Exception
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String handleBadRequests(Exception e) {
        return e.getMessage();
    }
}
