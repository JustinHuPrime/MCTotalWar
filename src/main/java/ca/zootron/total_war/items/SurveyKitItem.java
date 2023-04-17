package ca.zootron.total_war.items;

public class SurveyKitItem extends AbstractOreFinderItem {
  public static final int SEARCH_RADIUS = 10;

  public SurveyKitItem(Settings settings) {
    super(SEARCH_RADIUS, settings);
  }
}
