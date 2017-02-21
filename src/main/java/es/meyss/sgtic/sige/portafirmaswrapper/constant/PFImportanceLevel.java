package es.meyss.sgtic.sige.portafirmaswrapper.constant;

import es.meyss.sgtic.sige.portafirmas.client.ws.type.ImportanceLevel;

public final class PFImportanceLevel {
	public static final ImportanceLevel NORMAL = new ImportanceLevel("1", "");
	public static final ImportanceLevel HIGH = new ImportanceLevel("2", "");
	public static final ImportanceLevel VERY_HIGH = new ImportanceLevel("3", "");
	public static final ImportanceLevel URGENT = new ImportanceLevel("4", "");
}
