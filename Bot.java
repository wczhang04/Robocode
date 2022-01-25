package wz;
import robocode.*;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Bot - a robot by (Will Zhang)
 */
public class Bot extends TeamRobot
{
	int dist = 100;
	boolean stopWhenSeeRobot = false;
	/**
	 * run: Bot's default behavior
	 */
	public void run() {
		// Initialization of the robot should be put here

		// After trying out your robot, try uncommenting the import at the top,
		// and the next line:

		setColors(Color.black,Color.white,Color.white); // body,gun,radar

		while (true) {
			turnGunRight(360); // Scans automatically
		}
	}
	/**
	 * onScannedRobot: What to do when you see another robot
	 * Parts from Sample Robot TrackFire
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
		// Replace the next line with any behavior you would like
		double absoluteBearing = getHeading() + e.getBearing();
		double gunBearing = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
		double distance = e.getDistance();

		if (Math.abs(gunBearing) <= 3 && e.getDistance() < 500) {
			turnGunRight(gunBearing);
			fire(3);
		}
		else if (Math.abs(gunBearing) <= 3 && e.getDistance() > 500) {
			turnGunRight(gunBearing);
			fire(1);
		}
		else {
			turnGunRight(gunBearing);
		}
		// Generates another scan event if we see a robot.
		if (gunBearing == 0) {
			scan();
		}
	}

	/**
	 * onHitByBullet: What to do when you're hit by a bullet
	 * From Sample Robot Fire
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
		ahead(dist);
		dist *= -1;
		scan();
	}
}
