/*
 * Jipopro, the Jitsi Post-Processing application for recorded conferences.
 *
 * Distributable under LGPL license. See terms of license at gnu.org.
 */

package org.jitsi.recording.postprocessing.layout;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
//Disambiguation
import java.util.List;

/**
 * An implementation of {@link LayoutStrategy} that preserves the aspect ratios 
 * of the videos. If the small videos exceed band in the lower side of
 * the output video their sizes are reduced so that they fit the band exactly.
 * @author Vladimir Marinov
 *
 */
public class SmallVideosOverLargeVideoLayoutStrategy 
    extends AbstractLayoutStrategy {
    /**
     * The maximum height of small videos and respectively of the small videos
     * band
     */
    private final int MAX_SMALL_VIDEO_HEIGHT = 97;

    @Override
    protected void doCalculateDimensions() 
    {
        double widthToHeightRatio = 0.0;
        int smallVideosHeight = 0, largeVideoHeight;
        Dimension largeVideoDimensions = new Dimension();
        List<Dimension> smallVideoDimensions = new ArrayList<Dimension>();
        
        for (int i = 0; i < participants.size(); i++) 
        {
            if (!participants.get(i).isCurrentlySpeaking) 
            {
                widthToHeightRatio += 
                    AspectRatioUtil.scaleFactors
                        [participants.get(i).aspectRatio];
            }
        }
        
        if (widthToHeightRatio != 0) 
        {
            smallVideosHeight = 
                outputVideoWidth / widthToHeightRatio < MAX_SMALL_VIDEO_HEIGHT ?
                (int) (outputVideoWidth / widthToHeightRatio) : 
                MAX_SMALL_VIDEO_HEIGHT;
        }
        
        largeVideoHeight = outputVideoHeight;
        for (int i = 0; i < participants.size(); i++) 
        {
            if (!participants.get(i).isCurrentlySpeaking) 
            {
                smallVideoDimensions.add(new Dimension(
                (int) (smallVideosHeight * 
                AspectRatioUtil.scaleFactors[participants.get(i).aspectRatio]),
                smallVideosHeight));
            } else 
            {
                largeVideoDimensions = new Dimension(
                (int) (largeVideoHeight * 
                AspectRatioUtil.scaleFactors[participants.get(i).aspectRatio]),
                largeVideoHeight);
            }
        }
        
        this.smallVideosDimensions = smallVideoDimensions;
        this.largeVideoDimension = largeVideoDimensions;
    }

    @Override
    protected void doCalculatePositions() 
    {
        Point2D.Double[] smallVideosPositionsArray = 
            new Point2D.Double[smallVideosDimensions.size()];
        double currentSmallVideoXOffset;
        currentSmallVideoXOffset = outputVideoWidth;
        
        for (int i = smallVideosDimensions.size() - 1; i >= 0; i--)
        {
            currentSmallVideoXOffset -= smallVideosDimensions.get(i).width;
            smallVideosPositionsArray[i] = 
                new Point2D.Double(
                currentSmallVideoXOffset, 
                outputVideoHeight * 1.0 - smallVideosDimensions.get(i).height);
        }
        
        this.smallVideosPositions = 
            new ArrayList<Point2D.Double>(
                Arrays.asList(smallVideosPositionsArray));
    }
}
