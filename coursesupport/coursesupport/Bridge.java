package coursesupport;

import java.io.IOException;

import bridges.base.DataStruct;
import bridges.connect.Bridges;
import bridges.connect.DataSource;
import bridges.validation.RateLimitException;

import credentials.*;

public class Bridge {
	static private Bridges b = null; 

	
	
	private static Bridges instance() {
		if(credentials.Assignment.POST_TO_BRIDGES==false)
			return null;
		if(b==null) {
			b = new Bridges(Assignment.ASSIGNMENT_ID, User.USERNAME, User.APIKEY);
		}
		return b;
	}
	
	/**
	 *	@brief get the current data source object being used by BRIDGES
	 *
	 *	@return data source object
	 */
	public static DataSource getDataSource () {
		if(instance()==null)
			return null;
		return instance().getDataSource();
	}
	/**
	 * @brief Change the title of the assignment.
	 *
	 * The title is capped at MaxTitleSize characters.
	 *
	 * @param title title used in the visualization;
	 *
	 */
	public static void setTitle(String title) {
		if(instance()==null)
			return;
		instance().setTitle(title);
	}
	
	/**
	 * @brief Change the textual description of the assignment.
	 *
	 * This description is capped at MaxDescrSize characters.
	 *
	 * @param description description to annotate the visualization;
	 *
	 */
	public static void setDescription(String description) {
		if(instance()==null)
			return;
		instance().setDescription(description);
	}
	
	/**
	 *  @brief  sets the server type.
	 *
	 *	Options are: ['live', 'local', 'clone'], and 'live' is the default;
	 *
	 * 	@param  server server to which to connect.
	 *
	 */
	public static void setServer(String server) {
		if(instance()==null)
			return;
		instance().setServer(server);
	}
	
	/**
	 *  @brief  sets a debug flag, used for debugging BRIDGES
	 *
	 * 	@param  flag  boolean
	 *
	 */
	public static void setDebugFlag (Boolean flag) {
		if(instance()==null)
			return;
		instance().setDebugFlag(flag);;
	}

	/**
	 *  @brief  gets the  debug flag value, used for debugging BRIDGES
	 *
	 * 	@return  boolean flag
	 *
	 */
	public static Boolean getDebugFlag () {
		if(instance()==null)
			return false;
		return instance().getDebugFlag();
	}

	/**
	 *  @brief Turns on map overlay for subsequent visualizations - used with location specific
	 *  datasets
	 *
	 *  @param flag     this is the boolean flag for displaying a map overlay
	 *
	 **/
	public static void setMapOverlay (Boolean flag) {
		if(instance()==null)
			return;
		instance().setMapOverlay(flag);
	}
	
	
	/**
	 *  @brief Sets the type of map overlay to use
	 *
	 *  @param map     this is an Array describing the map overlay. The first element of the array is which map to use: "world" or "us"
	 *  and the second element is what attribute from the map to show: a country from world map, or a state from US map.
	 *
	 **/
	public static void setMap(String map, String info) {
		if(instance()==null)
			return;
		instance().setMap(map,  info);
	}

	/**
	 * Set the current assignment display mode to slide or stack, or throw an error;
	 * @param mode	One of: ['slide', 'stack'].
	 */
	public static void setDisplayMode(String mode) throws IllegalArgumentException {
		if(instance()==null)
			return;
		instance().setDisplayMode(mode);
	}


	/**
	 * 	@brief Sets the coordinate system type.
	 *
	 *	Coordinate system type options are: ['cartesian',
	 *	'albersusa', 'equirectangular', 'window'], and 'cartesian'
	 *	is the default; The "window" option
	 *	only works for graphs and will automatically scale the view on
	 *	the browser to include all vertices which have a fixed location.
	 *	A different window can be specified using setWindow().
	 *
	 * 	@param coord 	this is the desired coordinate space
	 *
	 **/
	public static void setCoordSystemType (String coord) {
		if(instance()==null)
			return;
		instance().setCoordSystemType(coord);
	}
	

	/**
	 * @brief Specify the window that will be used to render the view by default.
	 *
	 * This function enables specifying the window that will rendered by
	 *	default in the view. This only works for graph data types.
	 *	And the coordinate system need ot be set to "window" using
	 *	setCoordSystemType().
	 *
	 * 	@param x1 	minimum window x
	 * 	@param x2 	maximum window x
	 * 	@param y1 	minimum window y
	 * 	@param y2 	maximum window y
	 **/
	public static void setWindow (int x1, int x2, int y1, int y2) {
		if(instance()==null)
			return;
		instance().setWindow(x1, x2, y1, y2);
	}
	/**
	 * @brief Specify the window that will be used to render the view by default.
	 *
	 * This function enables specifying the window that will rendered by default in the view. This only works for graph data types. And the coordinate system need ot be set to "window" using setCoordSystemType().
	 *
	 * 	@param x1 	minimum window x
	 * 	@param x2 	maximum window x
	 * 	@param y1 	minimum window y
	 * 	@param y2 	maximum window y
	 **/
	public static void setWindow (float x1, float x2, float y1, float y2) {
		if(instance()==null)
			return;
		instance().setWindow(x1, x2, y1, y2);
	}
	/**
	 * @brief Specify the window that will be used to render the view by default.
	 *
	 * This function enables specifying the window that will rendered by default in the view. This only works for graph data types. And the coordinate system need ot be set to "window" using setCoordSystemType().
	 *
	 * 	@param x1 	minimum window x
	 * 	@param x2 	maximum window x
	 * 	@param y1 	minimum window y
	 * 	@param y2 	maximum window y
	 **/
	public static void setWindow (double x1, double x2, double y1, double y2) {
		if(instance()==null)
			return;
		instance().setWindow(x1,  x2,  y1,  y2);
	}

	/**
	 *	@brief Flag to control printing the JSON of the data structure.
	 *		Used only for debugging BRIDGES
	 * 	@return check if the flag to output the JSON is set
	**/
	public static boolean getJSONFlag() {
		if(instance()==null)
			return false;
		return instance().getJSONFlag();
	}

	/**
	 * 	@param flag the flag to print the JSON represenation of the data structure
	 *		to standard output. Used for debugging BRIDGES
	 **/
	public static void setJSONFlag (boolean flag) {
		if(instance()==null)
			return;
		instance().setJSONFlag(flag);
	}

	/**
	 * 	@param flag the flag to turn labels on/off on the entire visualization
     *
	 **/
	public static void setLabelFlag (boolean flag) {
		if(instance()==null)
			return;
		instance().setLabelFlag(flag);
	}

	/**
	 *  This method is used to suppress the visualization link that is
	 *  usually printed to the console
	 *
	 *  @param link_url_flag - flag  that controls if the link is printed
	 *              to console
	 *  @return none
	 *
	 */
	public static void  postVisualizationLink(boolean link_url_flag) {
		if(instance()==null)
			return;
		instance().postVisualizationLink(link_url_flag);
	}

	/**
	 *	@brief Get the assignment id
	 *
	 *  @return assignment as a string
	 *
	 */
	public static String getAssignment() {
		if(instance()==null)
			return null;
		return instance().getAssignment();
	}

	/**
	 *	@brief Get the assignment id as an integer
	 *
	 *  @return assignment as a string
	 *
	 */
	public static int getAssignmentID() {
		if(instance()==null)
			return -1;
		return instance().getAssignmentID();
	}


	/**
	 *	set the assignment id
	 *
	 * @param assignment number (int)
	 *
	 **/
	public static void setAssignment(int assignment) {
		if(instance()==null)
			return;
		instance().setAssignment(assignment);
	}

	/**
	 *
	 *	This exists to prevent duplicate error traces.
	 *
	 *	@return user id (string)
	 */
	public static String getUserName() {
		if(instance()==null)
			return null;
		return instance().getUserName();
	}

	/**
	 *	set User id
	 *
	 *	@param userName (string)
	 *
	 */
	public static void setUserName(String userName) {
		if(instance()==null)
			return;
		instance().setUserName(userName);
	}

	/**
	 *
	 *	Get application key
	 *
	 *	@return application key value (string)
	 *
	 */
	public static String getKey() {
		if(instance()==null)
			return null;
		return instance().getKey();
	}

	/**
	 *
	 *	Set application key
	 *
	 *	@param  key application key value (string)
	 *
	 */
	public static void setKey(String key) {
		if(instance()==null)
			return;
		instance().setKey(key);
	}

	/**
	 *
	 *  @brief Provide BRIDGES  a handle to the data structure to be visualized.
	 *
	 * 	This method sets  the handle to the current data structure; this can
	 *	be an array, the head of a linked list, root of a tree structure, a graph
	 *	Arrays of upto 3 dimensions are suppported. It can be any of the data
	 *	structures supported by BRIDGES. Polymorphism and type casting is used
	 *	to determine the actual data structure and extract its representtion.
	 *
	 * @param ds   The data structure to set (any of the subclasses of DataStruct)
	 *
	 */
	public static void setDataStructure(DataStruct ds) throws NullPointerException {
		if(instance()==null)
			return;
		instance().setDataStructure(ds);
	}

	/**
	 *
	 * This method deletes the user's current assignment from the Bridges server
	 *
	 * @throws IOException
	 */
	public static void clearAssignment() {
		if(instance()==null)
			return;
		instance().clearAssignment();
	}

	/**
	 *
	 * This method generates the representation of the current data structure (JSON)
	 * and sends that to the Bridges server for generating a visualization.
	 *
	 * @throws RateLimitException
	 * @throws IOException
	 */
	public static void visualize()  throws IOException, RateLimitException {
		if(instance()==null)
			return;
		instance().visualize();
	}
	
	
	
}
