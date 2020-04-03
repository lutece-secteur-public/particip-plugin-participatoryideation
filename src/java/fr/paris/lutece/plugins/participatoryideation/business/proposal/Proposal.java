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
package fr.paris.lutece.plugins.participatoryideation.business.proposal;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.participatoryideation.service.campaign.IdeationCampaignService;
import fr.paris.lutece.plugins.participatoryideation.util.Constants;
import fr.paris.lutece.portal.business.file.File;
import fr.paris.lutece.portal.service.resource.IExtendableResource;

/**
 * This is the business class for the object Proposal.
 */
public class Proposal implements Serializable, IExtendableResource
{

    public static final String PROPERTY_RESOURCE_TYPE = "PROPOSAL";
    public static final String WORKFLOW_RESOURCE_TYPE = "PARTICIPATORYIDEATION_PROPOSAL";

    public static final String LOCALISATION_TYPE_ARDT = "localized";
    public static final String LOCALISATION_TYPE_PARIS = "whole";

    public static final String ATTACHED_FILE_TYPE_DOC = "doc";
    public static final String ATTACHED_FILE_TYPE_IMG = "img";

    private static final String SEPARATOR_REFERENCE = "-";

    private static final long serialVersionUID = 1L;
    // Variables declarations
    private int _nId;
    private String _strLuteceUserName;
    private String _strTitre;
    private String _strDejadepose;
    private String _strCreationmethod;
    private String _strHandicap;
    private String _strHandicapComplement;
    private String _strOperatingbudget;
    private String _strDescription;
    private Long _nCout;
    private String _strCodeTheme;
    private String _strLocalisationType;
    private String _strLocalisationArdt;
    private String _strGeoJson;
    private Double _dLongitude;
    private Double _dLatitude;
    private String _strAdress;
    private Status _statusPublic;
    private String _strDepositaryType;
    private String _strDepositary;
    private boolean _bAcceptExploit;
    private boolean _bAcceptContact;
    private List<File> _listImgs;
    private List<File> _listDocs;
    private Timestamp _creationTimestamp;
    private String _strCodeCampaign;
    private int _nExportedTag;
    private int _nCodeProposal;
    private String _strTypeQpvQva;
    private String _strIdQpvQva;
    private String _strLibelleQpvQva;
    private Status _statusEudonet;
    private String _strMotifRecev;

    // Proposals that are sometimes only partially loaded for performance
    private List<Proposal> _listChildProposals;
    private List<Proposal> _listParentProposals;

    private String _strIdProjet;
    private String _strTitreProjet;
    private String _strUrlProjet;
    private String _strWinnerProjet;

    /**
     * Status of proposal
     */
    public enum Status
    {

        STATUS_DRAFT( "DRAFT", "participatoryideation.message.labelStatusDraft", false ), STATUS_SUBMITTED( "SUBMITTED",
                "participatoryideation.message.labelStatusSubmitted",
                true ), STATUS_EN_CO_CONSTRUCTION( "ENCOCONSTRUCTION", "participatoryideation.message.labelStatusEnCoConstruction", true ), STATUS_REGROUPE(
                        "REGROUPE", "participatoryideation.message.labelStatusRegroupe",
                        true ), STATUS_A_ETUDE( "AETUDE", "participatoryideation.message.labelAEtude", true ), STATUS_RETENU( "RETENU",
                                "participatoryideation.message.labelRetenu",
                                true ), STATUS_NON_RETENU( "NONRETENU", "participatoryideation.message.labelNonRetenu", true ), STATUS_SUPPRIME_PAR_USAGER(
                                        "SUPPRIMEPARUSAGER", "participatoryideation.message.labelSupprimeParUsager",
                                        false ), STATUS_SUPPRIME_PAR_MDP( "SUPPRIMEPARMDP", "participatoryideation.message.labelSupprimeParMdp", false );

        private static final Map<String, Status> valueMap;
        private static final List<Status> listStatusPublished = new ArrayList<>( );
        private static final List<Status> listStatusUnPublished = new ArrayList<>( );

        static
        {
            valueMap = new HashMap<>( );
            for ( Status s : Status.values( ) )
            {
                valueMap.put( s.strValue, s );
                if ( s.isPublished( ) )
                {
                    listStatusPublished.add( s );
                }
                else
                {
                    listStatusUnPublished.add( s );
                }
            }
        }

        public static Status getByValue( String strEtape )
        {
            return valueMap.get( strEtape );
        }

        public static List<Status> getAllStatusPublished( )
        {
            return listStatusPublished;
        }

        public static List<Status> getAllStatusUnPublished( )
        {
            return listStatusUnPublished;
        }

        private final String strValue;
        private final String strLibelle;
        private final boolean bPublished;

        Status( String strValue, String strMessage, boolean bPublished )
        {
            this.strValue = strValue;
            this.strLibelle = strMessage;
            this.bPublished = bPublished;
        }

        public String getValeur( )
        {
            return this.strValue;
        }

        public String getLibelle( )
        {
            return this.strLibelle;
        }

        public boolean isPublished( )
        {
            return this.bPublished;
        }
    }

    /**
     * Returns the reference of idea, formatted as "X-001234".
     */
    public static String constructsReference( String strCodeCampaign, int intCodeProposal )
    {
        if ( StringUtils.isBlank( strCodeCampaign ) )
        {
            return "?" + SEPARATOR_REFERENCE + String.format( "%06d", intCodeProposal );
        }
        else
        {
            return strCodeCampaign + SEPARATOR_REFERENCE + String.format( "%06d", intCodeProposal );
        }
    }

    /**
     * Returns the GeoJson
     * 
     * @return The GeoJson
     */
    public String getGeoJson( )
    {
        return _strGeoJson;
    }

    /**
     * Sets the GeoJson
     * 
     * @param The
     *            GeoJson
     */
    public void setGeoJson( String strGeoJson )
    {
        _strGeoJson = strGeoJson;
    }

    public Double getLatitude( )
    {
        return _dLatitude;
    }

    public void setLatitude( Double _dLatitude )
    {
        this._dLatitude = _dLatitude;
    }

    /**
     * Returns the Id
     * 
     * @return The Id
     */
    public int getId( )
    {
        return _nId;
    }

    /**
     * Sets the Id
     * 
     * @param nId
     *            The Id
     */
    public void setId( int nId )
    {
        _nId = nId;
    }

    /**
     * Returns the Titre
     * 
     * @return The Titre
     */
    public String getTitre( )
    {
        return _strTitre;
    }

    /**
     * Sets the Titre
     * 
     * @param strTitre
     *            The Titre
     */
    public void setTitre( String strTitre )
    {
        _strTitre = strTitre;
    }

    /**
     * Returns the Dejadepose
     * 
     * @return The Dejadepose
     */
    public String getDejadepose( )
    {
        return _strDejadepose;
    }

    /**
     * Sets the Dejadepose
     * 
     * @param strDejadepose
     *            The Dejadepose
     */
    public void setDejadepose( String strDejadepose )
    {
        _strDejadepose = strDejadepose;
    }

    /**
     * Sets the Creationmethod
     * 
     * @param strCreationmethod
     *            The Creationmethod
     */
    public void setCreationmethod( String strCreationmethod )
    {
        _strCreationmethod = strCreationmethod;
    }

    /**
     * Returns the Creationmethod
     * 
     * @return The Creationmethod
     */
    public String getCreationmethod( )
    {
        return _strCreationmethod;
    }

    public String getHandicap( )
    {
        return _strHandicap;
    }

    public void setHandicap( String _strHandicap )
    {
        this._strHandicap = _strHandicap;
    }

    public String getHandicapComplement( )
    {
        return _strHandicapComplement;
    }

    public void setHandicapComplement( String _strHandicapComplement )
    {
        this._strHandicapComplement = _strHandicapComplement;
    }

    /**
     * Sets the Operatingbudget
     * 
     * @param strOperatingbudget
     *            The Operatingbudget
     */
    public void setOperatingbudget( String strOperatingbudget )
    {
        _strOperatingbudget = strOperatingbudget;
    }

    /**
     * Returns the Operatingbudget
     * 
     * @return The Operatingbudget
     */
    public String getOperatingbudget( )
    {
        return _strOperatingbudget;
    }

    /**
     * Returns the Description
     * 
     * @return The Description
     */
    public String getDescription( )
    {
        return _strDescription;
    }

    /**
     * Sets the Description
     * 
     * @param strDescription
     *            The Description
     */
    public void setDescription( String strDescription )
    {
        _strDescription = strDescription;
    }

    /**
     * Returns the CodeTheme
     * 
     * @return The CodeTheme
     */
    public String getCodeTheme( )
    {
        return _strCodeTheme;
    }

    /**
     * Sets the CodeTheme
     * 
     * @param strCodeTheme
     *            The CodeTheme
     */
    public void setCodeTheme( String strCodeTheme )
    {
        _strCodeTheme = strCodeTheme;
    }

    /**
     * 
     * @return the lutece User Name
     */
    public String getLuteceUserName( )
    {
        return _strLuteceUserName;
    }

    /**
     * set Lutece User Name
     * 
     * @param _strLuteceUserName
     *            the lutece User Name
     */
    public void setLuteceUserName( String strLuteceUserName )
    {
        this._strLuteceUserName = strLuteceUserName;
    }

    /**
     * 
     * @return cout
     */
    public Long getCout( )
    {
        return _nCout;
    }

    /**
     * set Cout
     * 
     * @param _nCout
     *            cout
     */
    public void setCout( Long _nCout )
    {
        this._nCout = _nCout;
    }

    public String getLocalisationType( )
    {
        return _strLocalisationType;
    }

    public void setLocalisationType( String _strLocalisationType )
    {
        this._strLocalisationType = _strLocalisationType;
    }

    public String getAdress( )
    {
        return _strAdress;
    }

    public void setAdress( String _strAdress )
    {
        this._strAdress = _strAdress;
    }

    public String getLocalisationArdt( )
    {
        return _strLocalisationArdt;
    }

    public void setLocalisationArdt( String _strLocalisationArdt )
    {
        this._strLocalisationArdt = _strLocalisationArdt;
    }

    public String getDepositaryType( )
    {
        return _strDepositaryType;
    }

    public void setDepositaryType( String _strDepositaryType )
    {
        this._strDepositaryType = _strDepositaryType;
    }

    public Double getLongitude( )
    {
        return _dLongitude;
    }

    public void setLongitude( Double _dLongitude )
    {
        this._dLongitude = _dLongitude;
    }

    public boolean isAcceptExploit( )
    {
        return _bAcceptExploit;
    }

    public void setAcceptExploit( boolean _bAcceptExploit )
    {
        this._bAcceptExploit = _bAcceptExploit;
    }

    public boolean isAcceptContact( )
    {
        return _bAcceptContact;
    }

    public void setAcceptContact( boolean bAcceptContact )
    {
        this._bAcceptContact = bAcceptContact;
    }

    public String getDepositary( )
    {
        return _strDepositary;
    }

    public void setDepositary( String _strDepositary )
    {
        this._strDepositary = _strDepositary;
    }

    /**
     * GET IdProjet the id project
     * 
     * @return IdProjet the id project
     */
    public String getIdProjet( )
    {
        return _strIdProjet;
    }

    /**
     * SET IdProjet
     * 
     * @param strIDProjet
     *            the Id project
     */
    public void setIdProjet( String strIDProjet )
    {
        this._strIdProjet = strIDProjet;
    }

    /**
     * GET the title project
     * 
     * @return TitreProjet the title project
     */
    public String getTitreProjet( )
    {
        return _strTitreProjet;
    }

    /**
     * SET the title project
     * 
     * @param strTitreProjet
     *            the title project
     */
    public void setTitreProjet( String strTitreProjet )
    {
        this._strTitreProjet = strTitreProjet;
    }

    /**
     * Get the url project
     * 
     * @return the url project
     */
    public String getUrlProjet( )
    {
        return _strUrlProjet;
    }

    /**
     * Set the url project
     * 
     * @param strUrlProjet
     *            the url project
     */
    public void setUrlProjet( String strUrlProjet )
    {
        _strUrlProjet = strUrlProjet;
    }

    /**
     * Get winner projet value
     * 
     * @return the winner projet value
     */
    public String getWinnerProjet( )
    {
        return _strWinnerProjet;
    }

    /**
     * Set winner projet value
     * 
     * @param strUrlProjet
     *            the winner projet value
     */
    public void setWinnerProjet( String strWinnerProjet )
    {
        _strWinnerProjet = strWinnerProjet;
    }

    /**
     * @return the listImgs
     */
    public List<File> getImgs( )
    {
        return _listImgs;
    }

    /**
     * @param listImgs
     *            the listImgs to set
     */
    public void setImgs( List<File> listImgs )
    {
        this._listImgs = listImgs;
    }

    /**
     * @return the listDocs
     */
    public List<File> getDocs( )
    {
        return _listDocs;
    }

    /**
     * @param listDocs
     *            the listDocs to set
     */
    public void setDocs( List<File> listDocs )
    {
        this._listDocs = listDocs;
    }

    @Override
    public String getIdExtendableResource( )
    {
        return Integer.toString( _nId );
    }

    @Override
    public String getExtendableResourceType( )
    {
        return PROPERTY_RESOURCE_TYPE;
    }

    @Override
    public String getExtendableResourceName( )
    {
        return _strTitre;
    }

    @Override
    public String getExtendableResourceDescription( )
    {
        return _strDescription;
    }

    @Override
    public String getExtendableResourceImageUrl( )
    {
        return null;
    }

    /**
     * @return the _creationTimestamp
     */
    public Timestamp getCreationTimestamp( )
    {
        return _creationTimestamp;
    }

    /**
     * @param _creationTimestamp
     *            the _creationTimestamp to set
     */
    public void setCreationTimestamp( Timestamp _creationTimestamp )
    {
        this._creationTimestamp = _creationTimestamp;
    }

    /**
     * Returns the exported Tag
     * 
     * @return The exported Tag
     */
    public int getExportedTag( )
    {
        return _nExportedTag;
    }

    /**
     * Sets the _nExportedTag
     * 
     * @param The
     *            Exported Tagd
     * 
     */
    public void setExportedTag( int nExportedTag )
    {
        _nExportedTag = nExportedTag;
    }

    /**
     * @return the CodeProposal
     */
    public int getCodeProposal( )
    {
        return _nCodeProposal;
    }

    /**
     * @param CodeProposal
     *            the CodeProposal to set
     */
    public void setCodeProposal( int iCodeProposal )
    {
        this._nCodeProposal = iCodeProposal;
    }

    /**
     * @return the TypeQpvQva
     */
    public String getTypeQpvQva( )
    {
        return _strTypeQpvQva;
    }

    /**
     * @param strTypeQpvQva
     *            the TypeQpvQva to set
     */
    public void setTypeQpvQva( String strTypeQpvQva )
    {
        this._strTypeQpvQva = strTypeQpvQva;
    }

    /**
     * @return the IdQpvQva
     */
    public String getIdQpvQva( )
    {
        return _strIdQpvQva;
    }

    /**
     * @param strIdQpvQva
     *            the IdQpvQva to set
     */
    public void setIdQpvQva( String strIdQpvQva )
    {
        this._strIdQpvQva = strIdQpvQva;
    }

    /**
     * @return the LibelleQpvQva
     */
    public String getLibelleQpvQva( )
    {
        return _strLibelleQpvQva;
    }

    /**
     * @param strLibelleQpvQva
     *            the LibelleQpvQva to set
     */
    public void setLibelleQpvQva( String strLibelleQpvQva )
    {
        this._strLibelleQpvQva = strLibelleQpvQva;
    }

    public String getReference( )
    {
        return constructsReference( getCodeCampaign( ), getCodeProposal( ) );
    }

    public Status getStatusPublic( )
    {
        return _statusPublic;
    }

    public void setStatusPublic( Status statusPublic )
    {
        this._statusPublic = statusPublic;
    }

    public Status getStatusEudonet( )
    {
        return _statusEudonet;
    }

    public void setStatusEudonet( Status statusEudonet )
    {
        this._statusEudonet = statusEudonet;
    }

    public String getMotifRecev( )
    {
        return _strMotifRecev;
    }

    public void setMotifRecev( String _strMotifRecev )
    {
        this._strMotifRecev = _strMotifRecev;
    }

    /**
     * @return the listChildProposals
     */
    public List<Proposal> getChildProposals( )
    {
        return _listChildProposals;
    }

    /**
     * @param listChildProposals
     *            the listChildProposals to set
     */
    public void setChildProposals( List<Proposal> listChildProposals )
    {
        this._listChildProposals = listChildProposals;
    }

    /**
     * @return the listParentProposals
     */
    public List<Proposal> getParentProposals( )
    {
        return _listParentProposals;
    }

    /**
     * @param listParentProposals
     *            the listParentProposals to set
     */
    public void setParentProposals( List<Proposal> listParentProposals )
    {
        this._listParentProposals = listParentProposals;
    }

    /**
     * Returns trie if the proposal can be removed by its submitter. Removing nedd ideation phase to be open.
     */
    public boolean canDelete( )
    {
        return IdeationCampaignService.getInstance( ).isDuring( _strCodeCampaign, Constants.IDEATION );
    }

    /**
     * 
     * @return boolean
     */
    public boolean getStatusIsRemoved( )
    {
        return ( _statusPublic.getValeur( ).equals( Status.STATUS_SUPPRIME_PAR_MDP.getValeur( ) )
                || _statusPublic.getValeur( ).equals( Status.STATUS_SUPPRIME_PAR_USAGER.getValeur( ) ) );
    }

    /* ********************************************************************************************* */
    /* * CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN * */
    /* * CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN CAMPAIGN * */
    /* ********************************************************************************************* */
    /* * Each campaign is represented by a character : A = 2014, B = 2015, etc. * */
    /* ********************************************************************************************* */

    /**
     * @return the CodeCampaign
     */
    public String getCodeCampaign( )
    {
        return _strCodeCampaign;
    }

    /**
     * @param _strCodeCampaign
     *            the CodeCampaign to set
     */
    public void setCodeCampaign( String _strCodeCampaign )
    {
        this._strCodeCampaign = _strCodeCampaign;
    }

}