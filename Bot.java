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
	
		// Team colors: All white with black body
		setColors(Color.black,Color.white,Color.white); // body,gun,radar

		while (true) {
			turnGunRight(360); // Scans automatically
		}
	}
	/**
	 * Parts from Sample Robot TrackFire
	 */
	public void onScannedRobot(ScannedRobotEvent e) {
	// Don't fire at teammates! Returns true if scanned teammate
		if(isTeammate(e.getName())) { 
			return;
		}
		if(isTeammate(e.getName()) == false) {
			// Find position of scanned Robot
			double absoluteBearing = getHeading() + e.getBearing();
			double gunBearing = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());
			double distance = e.getDistance();
			// If accurate enough and within distance of 500, shoot hard with 3 
			if (Math.abs(gunBearing) <= 3 && e.getDistance() < 500) {
				turnGunRight(gunBearing);
				fire(3);
			}
			// If accurate enough but at distance greater than 500, shoot lighter with 1
			else if (Math.abs(gunBearing) <= 3 && e.getDistance() > 500) {
				turnGunRight(gunBearing);
				fire(1);
			}
			// Otherwise just scan
			else {
				turnGunRight(gunBearing);
			}
			// Generates another scan event if we see a robot
			if (gunBearing == 0) {
				scan();
			}
		}
	}
	/**
	 * From Sample Robot Fire
	 */
	public void onHitByBullet(HitByBulletEvent e) {
		// Move perpendicular to where bullet came from
		turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));
		ahead(dist);
		dist *= -1;
		scan();
	}
}
