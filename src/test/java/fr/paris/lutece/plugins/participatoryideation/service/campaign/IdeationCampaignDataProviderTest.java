/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.participatoryideation.service.campaign;

import fr.paris.lutece.plugins.participatoryideation.business.proposal.Proposal;
import fr.paris.lutece.plugins.participatoryideation.util.ParticipatoryIdeationConstants;
import fr.paris.lutece.test.LuteceTestCase;

/**
 * SubmitterTypeTest
 */
public class IdeationCampaignDataProviderTest extends LuteceTestCase
{
    public void testBusiness( )
    {
        // Initialize an object
        IIdeationCampaignDataProvider instance = IdeationCampaignDataProvider.getInstance( );

        // Spring bean instantiation
        assertEquals( IdeationCampaignDataProvider.class, instance.getClass( ) );

        // Methods about campaign
        assertEquals( 2, instance.getCampaigns( ).size( ) );
        assertEquals( "A", instance.getCampaigns( ).iterator( ).next( ).getCode( ) );
        assertEquals( "First ideation campaign", instance.getCampaigns( ).iterator( ).next( ).getName( ) );

        // Methods about phases
        assertEquals( true, instance.isAfterBeginning( ParticipatoryIdeationConstants.IDEATION ) );
        assertEquals( true, instance.isAfterBeginning( "B", ParticipatoryIdeationConstants.IDEATION ) );

        assertEquals( false, instance.isAfterEnd( ParticipatoryIdeationConstants.IDEATION ) );
        assertEquals( false, instance.isAfterEnd( "B", ParticipatoryIdeationConstants.IDEATION ) );

        assertEquals( false, instance.isBeforeBeginning( ParticipatoryIdeationConstants.IDEATION ) );
        assertEquals( false, instance.isBeforeBeginning( "B", ParticipatoryIdeationConstants.IDEATION ) );

        assertEquals( true, instance.isBeforeEnd( ParticipatoryIdeationConstants.IDEATION ) );
        assertEquals( true, instance.isBeforeEnd( "B", ParticipatoryIdeationConstants.IDEATION ) );

        assertEquals( true, instance.isDuring( ParticipatoryIdeationConstants.IDEATION ) );
        assertEquals( true, instance.isDuring( "B", ParticipatoryIdeationConstants.IDEATION ) );

        // Methods about areas
        assertEquals( 0, instance.getLastCampaignAllAreaLabels( ).size( ) );
        assertEquals( 0, instance.getCampaignAllAreaLabels( "B" ).size( ) );

        assertEquals( 0, instance.getLastCampaignLocalizedAreaLabels( ).size( ) );
        assertEquals( 0, instance.getCampaignLocalizedAreaLabels( "B" ).size( ) );

        assertEquals( 0, instance.getLastCampaignNumberLocalizedAreas( ) );
        assertEquals( 0, instance.getCampaignNumberLocalizedAreas( "B" ) );

        assertEquals( Proposal.LOCATION_AREA_TYPE_WHOLE, instance.getLastCampaignWholeAreaLabel( ).getCode()  );
        assertEquals( Proposal.LOCATION_AREA_TYPE_WHOLE, instance.getCampaignWholeAreaLabel( "B" ).getCode() );

        // Methods about themes
        assertEquals( 3, instance.getLastCampaignThemes( ).size( ) );
        assertEquals( 3, instance.getCampaignThemes( "B" ).size( ) );
    }
}
