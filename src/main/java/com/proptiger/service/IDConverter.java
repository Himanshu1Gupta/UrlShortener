package com.proptiger.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IDConverter {
	

	static {
		initializeCharToIndexTable();
		initializeIndexToCharTable();
	}

	private static HashMap<Character, Integer> charToIndexTable;
	private static List<Character> indexToCharTable;
	private static long arr[] = {

			1, 89, 7921, 704969, 62742241, 584059414, 981287489, 334585912, 778145965, 254990402, 694145624, 778960109,
			327449218, 142980199, 725237627, 546148355, 607203259, 41089673, 656980876, 471297558, 945482375, 147930787,
			165839952, 759755630, 618250601, 24303104, 162976242, 504885440, 934803852, 197542247, 581259864, 732127539,
			159350516, 182195826, 215428402, 173127645, 408360300, 344066448, 621913662, 350315533, 178082220,
			849317475, 589254750, 443672386, 486842081, 328944908, 276096609, 572598033, 961224587, 548987648,
			859900336, 531129372, 270513779, 75726163, 739628465, 826932930, 597030259, 135692680, 76648436, 821710762,
			132257307, 770900246, 610121418, 300805824, 771718154, 682915230, 779455050, 371498967, 63407832, 643297013,
			253433758, 555604308, 448783069, 941692868, 810664671, 149155215, 274814044, 458449748, 802027292,
			380428491, 858135468, 374056120, 290994449, 898505786, 967014401, 64281087, 721016708, 170486564, 173304091,
			424063994, 741695207, 10872961, 967693529, 124723479, 100389554, 934670250, 185651669, 522998429, 546859859,
			670527115, 676912822, 245240738, 826425535, 551872104, 116616913, 378905187, 722561412, 307965220,
			408904391, 392490547, 931658445, 917601031, 666491192, 317715675, 276694879, 625844063, 700121222,
			310788324, 660160647, 754297177, 132448284, 787897199, 122850221, 933669599, 96593730, 596841914, 118929975,
			584767705, 44325381, 944958888, 101340444, 19299453, 717651310, 870966149, 515986722, 922817943, 130796353,
			640875340, 37904861, 373532608, 244401881, 751767262, 907285856, 748440624, 611215074, 398141208, 434567267,
			676486497, 207297813, 449505231, 5965279, 530909831, 250974630, 336741916, 970030321, 332697967, 610118860,
			300578162, 751456236, 879604542, 284803692, 347528413, 930028547, 772540109, 756069225, 290160556,
			824289309, 361747990, 195570886, 405808735, 116977163, 410967437, 576101641, 273045692, 301066420,
			794911198, 747096132, 491555286, 748420153, 609393155, 235990417, 3146966, 280079974, 927117518, 513458528,
			697808677, 104971819, 342491828, 481772482, 877750604, 119803210, 662485620, 961219774, 548559291,
			821776563, 138113596, 292109960, 997786265, 802976969, 464949744, 380526929, 866896450, 153783511,
			686732388, 119182105, 607207275, 41447097, 688791612, 302453041, 918320467, 730520996, 16368189, 456768814,
			652424166, 65750368, 851782717, 808661288, 970854135, 406017413, 135549505, 63905861, 687621594, 198321439,
			650607952, 904107329, 465551721, 434102882, 635156232, 528904256, 72478455, 450582453, 101838037, 63585230,
			659085435, 658603309, 615694095, 796774077, 912892363, 247419740, 20356706, 811746827, 245467099, 846571664,
			344877571, 694103609, 775220774, 994648410, 523707874, 610000464, 290040918, 813641527, 414095399,
			854490259, 49632519, 417294163, 139180248, 387041988, 446736694, 759565493, 601328408, 518227941, 122286427,
			883491933, 630781491, 139552307, 420155239, 393816012, 49624823, 416609219, 78220232, 961600606, 582453339,
			838346814, 612865928, 545067214, 510981710, 477371875, 486096581, 262595408, 370991151, 18212208, 620886505,
			258898560, 41971679, 735479410, 457667035, 732365835, 180558860, 69738428, 206720050, 398084324, 429504591,
			225908333, 105841497, 419893170, 370491871, 973776295, 666089653, 281978704, 96104481 };
	private static int mod = (int) 1e9 + 7;

	/**
	 * To initialize mapping of char to number - called in static block
	 * 
	 * @param 
	 * @return 
	 */
	private static void initializeCharToIndexTable() {
		charToIndexTable = new HashMap<Character, Integer>();
		// 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
		for (int i = 0; i < 26; ++i) {
			char c = 'a';
			c += i;
			charToIndexTable.put(c, i);
		}
		for (int i = 26; i < 52; ++i) {
			char c = 'A';
			c += (i - 26);
			charToIndexTable.put(c, i);
		}
		for (int i = 52; i < 62; ++i) {
			char c = '0';
			c += (i - 52);
			charToIndexTable.put(c, i);
		}
		// -._~:/?#[]@!$&'()*+,;=
		charToIndexTable.put('-', 62);
		charToIndexTable.put('.', 63);
		charToIndexTable.put('_', 64);
		charToIndexTable.put('~', 65);
		charToIndexTable.put(':', 66);
		charToIndexTable.put('/', 67);
		charToIndexTable.put('?', 68);
		charToIndexTable.put('#', 69);
		charToIndexTable.put('[', 70);
		charToIndexTable.put(']', 71);
		charToIndexTable.put('@', 72);
		charToIndexTable.put('!', 73);
		charToIndexTable.put('$', 74);
		charToIndexTable.put('&', 75);
		// charToIndexTable.put('', 76);
		charToIndexTable.put('(', 77);
		charToIndexTable.put(')', 78);
		charToIndexTable.put('*', 79);
		charToIndexTable.put('+', 80);
		charToIndexTable.put(',', 81);
		charToIndexTable.put(';', 82);
		charToIndexTable.put('=', 83);

	}

	/**
	 * Add all possible characters in ArrayList - called in static block
	 * 
	 * @param 
	 * @return 
	 */
	private static void initializeIndexToCharTable() {
		// 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
		indexToCharTable = new ArrayList<Character>();
		for (int i = 0; i < 26; ++i) {
			char c = 'a';
			c += i;
			indexToCharTable.add(c);
		}
		for (int i = 26; i < 52; ++i) {
			char c = 'A';
			c += (i - 26);
			indexToCharTable.add(c);
		}
		for (int i = 52; i < 62; ++i) {
			char c = '0';
			c += (i - 52);
			indexToCharTable.add(c);
		}
	}

	/**
	 * Generate shortUrl for a given Id, internally calls Base10to62 encoding method
	 * 
	 * @param id- primary key of database, corresponding to which we generate UniqueString
	 * @return shortUrl, in our case it is 7 character
	 */
	public static String createUniqueID(int id) {
		List<Integer> base62ID = convertBase10ToBase62ID(id);
		StringBuilder uniqueURLID = new StringBuilder();
		for (int digit : base62ID) {
			uniqueURLID.append(indexToCharTable.get(digit));
		}
		String g = uniqueURLID.toString();
		String s = "";
		for (int i = g.length(); i < 7; i++)
			s += 'a';
		return s + g;
	}

	/**
	 * converts id(Base10) to (uniqueString)Base62 
	 * 
	 * @param id- primary key of database, corresponding to which we generate UniqueString
	 * @return LinkedList of Base62 characters, which we convert to String in createUniqueId
	 */
	private static List<Integer> convertBase10ToBase62ID(int id) {
		LinkedList<Integer> digits = new LinkedList<Integer>();
		while (id > 0) {
			int remainder = (int) (id % 62);
			((LinkedList<Integer>) digits).addFirst(remainder);
			id /= 62;
		}
		return digits;
	}

	
	/**
	 * Generate Id for a given shortUrl, internally calls Base62to10 encoding and provide it with Character arraylist of ShortUrl 
	 * 
	 * @param UniqueString- corresponding to which we generate dictionary Key
	 * @return Id 
	 */
	public static int getDictionaryKeyFromUniqueID(String uniqueID) {
		List<Character> base62Number = new ArrayList<Character>();
		for (int i = 0; i < uniqueID.length(); ++i) {
			base62Number.add(uniqueID.charAt(i));
		}
		int dictionaryKey = convertBase62ToBase10ID(base62Number);
		return dictionaryKey;
	}

	
	/**
	 * converts uniqueString(Base62) to id(Base10) 
	 * 
	 * @param  List of Characters corresponding to which we generate dictionaryKey
	 * @return id(dictionary key)
	 */
	private static int convertBase62ToBase10ID(List<Character> ids) {
		int id = 0;
		int exp = ids.size() - 1;
		for (int i = 0; i < ids.size(); ++i, --exp) {
			int base10 = charToIndexTable.get(ids.get(i));
			id += (base10 * Math.pow(62.0, exp));
		}
		return id;
	}

	
	/**
	 * Generate HashUrl for the longUrl 
	 * 
	 * @param longUrl - a string
	 * @return hashValue - an integer
	 */
	public static int hashUrl(String longUrl) {
		int len = longUrl.length();
		
		long hashVal = 0;
		for (int i = 0; i < len; i++) {
			char c = longUrl.charAt(i);
			long val = charToIndexTable.get(c);
			long v = (val * arr[i]) % mod;
			hashVal = (hashVal + v) % mod;
		}
		return (int) hashVal;
	}

	private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    
    
    /**
	 * Used to check whether the url is valid or not 
	 * 
	 * @param url -  a string
	 * @return boolean - true if valid url otherwise false
	 */
    public static boolean validateURL(String url) {
        Matcher m = URL_PATTERN.matcher(url);
        return m.matches();
    }
    
    /**
	 * Used to compare two Dates - if older date is year old than new
	 * 
	 * @param two dates 
	 * @return return true if it is a year old otherwise false
	 */
	public static boolean compare(Date d1, Date d2) {
		@SuppressWarnings("deprecation")
		int y1 = d1.getYear();
		@SuppressWarnings("deprecation")
		int y2 = d2.getYear();
		@SuppressWarnings("deprecation")
		int m1 = d1.getMonth();
		@SuppressWarnings("deprecation")
		int m2 = d2.getMonth();

		if (y2 - y1 > 1) {
			return true;
		} else if (y2 - y1 == 1) {
			if (m2 >= m1)
				return true;
			return false;
		} else
			return false;
	}
}
