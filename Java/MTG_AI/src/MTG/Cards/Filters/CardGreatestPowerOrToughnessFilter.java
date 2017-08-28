package MTG.Cards.Filters;

import MTG.Cards.IMagicCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CardGreatestPowerOrToughnessFilter implements ICardFilter {

    static private final String POWER_TOUGH_PATTERN = "^(?:(?:[0-9](?:\\.[0-9])?)+)$";
    static private final String[] MODES = { "toughness", "power" };
    private String mode;


    public CardGreatestPowerOrToughnessFilter(final String mode) {

        if(!Arrays.asList(MODES).contains(mode.toLowerCase())) throw new IllegalArgumentException("Invalid mode " + mode);

        this.mode = mode.toLowerCase();
    }

    @Override
    public List<IMagicCard> query(List<IMagicCard> cards) {

        List<IMagicCard> filteredList = new ArrayList<>();
        double max = -1;
        Pattern digitP = Pattern.compile(POWER_TOUGH_PATTERN);
        Matcher match;
        String strValue;


        for(IMagicCard card : cards) {

            if(mode.equals(MODES[0])) strValue = card.getToughness();
            else strValue = card.getPower();

            if(strValue == null) continue;

            match = digitP.matcher(strValue);
            if(!match.find()) continue;

            double value = Double.parseDouble(strValue);

            if(filteredList.isEmpty()) {
                filteredList.add(card);
                max = value;
            }
            else if(value == max) {
                filteredList.add(card);
            }
            else if(value > max) {
                filteredList.clear();
                filteredList.add(card);
                max = value;
            }
        }

        return filteredList;
    }
}
