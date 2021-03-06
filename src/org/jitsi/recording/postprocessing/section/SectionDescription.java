/*
 * Jipopro, the Jitsi Post-Processing application for recorded conferences.
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */

package org.jitsi.recording.postprocessing.section;

import java.awt.*;
import java.awt.geom.*;
//Disambiguation
import java.util.List;

import org.jitsi.recording.postprocessing.layout.*;
import org.jitsi.recording.postprocessing.participant.*;

/**
 * Contains the info that is needed in order to process a single section
 * @author Vladimir Marinov
 *
 */
public class SectionDescription {
    /** The list of participants that are active during this section */
    public List<ParticipantInfo> activeParticipants;
    
    /** The dimensions of the large video */
    public Dimension largeVideoDimension;
    
    /** The dimensions of the small videos */
    public List<Dimension> smallVideosDimensions;
    
    /** The positions of the small videos */
    public List<Point2D.Double> smallVideosPositions;
    
    /** The section starting instant */
    public int startInstant;
    
    /** The section ending instant */
    public int endInstant;
    
    /** The sequence number of this section (starting with 0) */
    public int sequenceNumber;
}
