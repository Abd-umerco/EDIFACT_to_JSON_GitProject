package project.edifact_to_json_git;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class integrations

{
	// ---( internal utility methods )---

	final static integrations _instance = new integrations();

	static integrations _newInstance() { return new integrations(); }

	static integrations _cast(Object o) { return (integrations)o; }

	// ---( server methods )---




	public static final void parse850 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(parse850)>> ---
		// @sigtype java 3.5
		IDataCursor cursor = pipeline.getCursor();
		    
		    // Get unDefData from pipeline
		    String unDefData = (String) IDataUtil.get(cursor, "unDefData");
		    
		    if (unDefData == null || unDefData.trim().isEmpty()) {
		        IDataUtil.put(cursor, "xml850", "<X12_850/>");
		        cursor.destroy();
		        return;
		    }
		
		    // Parse segments
		    String[] segments = unDefData.split("~");
		    StringBuilder xml = new StringBuilder();
		    xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		    xml.append("<X12_850>\n");
		
		    for (String segment : segments) {
		        segment = segment.trim();
		        if (segment.isEmpty()) continue;
		
		        String[] elements = segment.split("\\*", -1);
		        String segId = elements[0].trim();
		
		        xml.append("  <").append(segId).append(">\n");
		        for (int i = 1; i < elements.length; i++) {
		            String elementId = segId + String.format("%02d", i);
		            xml.append("    <").append(elementId).append(">")
		               .append(elements[i])
		               .append("</").append(elementId).append(">\n");
		        }
		        xml.append("  </").append(segId).append(">\n");
		    }
		
		    xml.append("</X12_850>");
		
		    // Put result back in pipeline
		    IDataUtil.put(cursor, "xml850", xml.toString());
		    cursor.destroy();
		// --- <<IS-END>> ---

                
	}
}

