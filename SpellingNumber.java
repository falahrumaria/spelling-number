package rumaria.library;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
 * written by rumaria
 */
public class SpellingNumber {

	private static final Map<Integer, String[]> numberMap = getNumberMap();
	private static final Map<Integer, String> levelMap = getLevelMap();

	public static void main(String[] args) {
		// long number = 78510610008519418l;
		// long number = 7007007007007007000l;
		// long number = 1000000000110000000l;
		// long number = 1171111111111111111l;
		// long number = 5555555555555555555l;
		// int number = 12010090;
		// long number = 200080080740560l;
		// int number = 1007770001;
//		int number = 10005;
		BigDecimal number = new BigDecimal("3299810.000");
		System.out.println(number);
		System.out.println("\n\nresult:\n" + spellNumber(number));
	}

	public static String spellNumber(long number) {
		Integer digit;
		Stack<String> textStack = new Stack<>();
		Stack<Integer> numberStack = new Stack<>();
		boolean isLevelAdded = false;
		do {
			digit = (int) (number % 10);
			number = number / 10;
			isLevelAdded = addToStack(digit, isLevelAdded, textStack, numberStack);
		} while (number != 0);
		return getNumberText(textStack);
	}

	public static String spellNumber(BigDecimal bd) {
		String str = bd.toString();
		String[] arr = str.split("\\.");
		long number = Long.parseLong(arr[0]);
		return spellNumber(number);
	}

	private static Map<Integer, String[]> getNumberMap() {
		Map<Integer, String[]> map = new HashMap<>();
		map.put(0, new String[] { "", "ten" });
		map.put(1, new String[] { "one", "eleven" });
		map.put(2, new String[] { "two", "twelve", "twenty" });
		map.put(3, new String[] { "three", "thirteen", "thirty" });
		map.put(4, new String[] { "four", "fourteen", "forty" });
		map.put(5, new String[] { "five", "fifteen", "fifty" });
		map.put(6, new String[] { "six", "sixteen", "sixty" });
		map.put(7, new String[] { "seven", "seventeen", "seventy" });
		map.put(8, new String[] { "eight", "eighteen", "eighty" });
		map.put(9, new String[] { "nine", "nineteen", "ninety" });
		return map;
	}

	private static Map<Integer, String> getLevelMap() {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "thousand");
		map.put(2, "million");
		map.put(3, "billion");
		map.put(4, "trillion");
		map.put(5, "quadrillion");
		map.put(0, "quintillion");
		return map;
	}

	private static boolean addToStack(int digit, boolean isLevelAdded, Stack<String> textStack,
			Stack<Integer> numberStack) {
		int position = numberStack.size() % 3; // posisi dalam 3 pasang angka
		String text;
		switch (position) {
		case 0: // satuan
			System.out.println("satuan :");
			text = numberMap.get(digit)[0];
			isLevelAdded = tryPushAndAddLevel(text, textStack, numberStack.size());
			tryPeek(textStack);
			break;
		case 1: // puluhan atau belasan
			System.out.println("puluhan :");
			if (digit == 1) {
				// berarti belasan atau sepuluh
				if (numberStack.peek() != 0) {
					textStack.pop();
				}
				text = numberMap.get(numberStack.peek())[1];
				if (!isLevelAdded) {
					isLevelAdded = tryPushAndAddLevel(text, textStack, numberStack.size());
				} else {
					textStack.push(text);
				}
			} else if (digit != 0) {
				text = numberMap.get(digit)[2];
				if (!isLevelAdded) {
					isLevelAdded = tryPushAndAddLevel(text, textStack, numberStack.size());
				} else {
					textStack.push(text);
				}
			}
			tryPeek(textStack);
			break;
		case 2: // ratusan
			System.out.println("ratusan :");
			if (digit != 0) {
				text = numberMap.get(digit)[0] + " hundred";
				if (!isLevelAdded) {
					isLevelAdded = tryPushAndAddLevel(text, textStack, numberStack.size());
				} else {
					textStack.push(text);
				}
			}
			tryPeek(textStack);
			break;
		}
		numberStack.push(digit);
		return isLevelAdded;
	}

	private static boolean tryPushAndAddLevel(String str, Stack<String> textStack, int numberStackSize) {
		System.out.println("   masuk 1");
		if (numberStackSize >= 3) {
			System.out.println("   masuk 2.1");
			int position = numberStackSize / 3 % levelMap.size(); // 3 pasang angka di posisi yg ke-berapa
			if (!str.isEmpty()) {
				textStack.push(levelMap.get(position) + ",");
				textStack.push(str);
				return true;
			}
		} else if (!str.isEmpty()) {
			System.out.println("   masuk 2.2");
			textStack.push(str);
		}
		return false;
	}

	private static void tryPeek(Stack<String> textStack) {
		if (!textStack.isEmpty()) {
			System.out.println("   " + textStack.peek());
		}
	}

	private static String getNumberText(Stack<String> stack) {
		StringBuilder sb = new StringBuilder();
		while (!stack.isEmpty()) {
			sb.append(stack.pop() + " ");
		}
		return sb.toString();
	}

}
