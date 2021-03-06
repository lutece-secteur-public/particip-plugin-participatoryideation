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

import java.util.Collection;
import java.util.Map;

import fr.paris.lutece.plugins.participatoryideation.business.submitter.SubmitterType;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;

/**
 * Interface for providing informations about ideation campaign.
 */
public interface IIdeationCampaignDataProvider
{

    public static final String WHOLE_AREA = "whole city";

    // *********************************************************************************************
    // * CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN *
    // * CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN *
    // *********************************************************************************************

    // Provides list of campaigns
    public ReferenceList getCampaigns( );

    // Provides last campaign, sorted by code
    public ReferenceItem getLastCampaign( );

    // *********************************************************************************************
    // * PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASE *
    // * PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASES PHASE *
    // *********************************************************************************************

    // Provides informations about campaign dates.
    public boolean isBeforeBeginning( String codeCampaign, String phase );

    public boolean isAfterBeginning( String codeCampaign, String phase );

    public boolean isDuring( String codeCampaign, String phase );

    public boolean isBeforeEnd( String codeCampaign, String phase );

    public boolean isAfterEnd( String codeCampaign, String phase );

    // Same as precedent, for last campaign.
    public boolean isBeforeBeginning( String phase );

    public boolean isAfterBeginning( String phase );

    public boolean isDuring( String phase );

    public boolean isBeforeEnd( String phase );

    public boolean isAfterEnd( String phase );

    // *********************************************************************************************
    // * AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA *
    // * AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA AREA *
    // *********************************************************************************************

    // Provides informations about area.

    public ReferenceList getCampaignAllAreaLabels( String codeCampaign );

    public ReferenceList getCampaignAllAreaTypes( String codeCampaign );

    public ReferenceItem getCampaignWholeAreaLabel( String codeCampaign );

    public ReferenceList getCampaignLocalizedAreaLabels( String codeCampaign );

    public int getCampaignNumberLocalizedAreas( String codeCampaign );

    // Same as precedent, for last campaign.

    public ReferenceList getLastCampaignAllAreaLabels( );

    public ReferenceList getLastCampaignAllAreaTypes( );

    public ReferenceItem getLastCampaignWholeAreaLabel( );

    public ReferenceList getLastCampaignLocalizedAreaLabels( );

    public int getLastCampaignNumberLocalizedAreas( );

    // *********************************************************************************************
    // * SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER *
    // * SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER SUBMITTER *
    // *********************************************************************************************

    // Provides informations about submitter types
    public Collection<SubmitterType> getCampaignSubmitterTypes( String codeCampaign );

    // *********************************************************************************************
    // * THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEME *
    // * THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEMES THEME *
    // *********************************************************************************************

    // Return theme names of a campaign
    public ReferenceList getCampaignThemes( String codeCampaign );

    // Return theme names of last campaign
    public ReferenceList getLastCampaignThemes( );

    // Return theme front rgb of a campaign
    public ReferenceList getCampaignThemesFrontRgb( String codeCampaign );

    // *********************************************************************************************
    // * FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELD *
    // * FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELDS FIELD *
    // *********************************************************************************************

    // Return field data :
    // data[0] = 1 if active, but 0
    // data[1] = label of the field
    // data[2] = description of the field
    // data[3] = 1 if mandatory, but 0
    public String [ ] getCampaignFieldData( String codeCampaign, String fieldCode );

    // Return data of all fields of a campaign
    public Map<String, String [ ]> getCampaignFieldsData( String codeCampaign );

}
