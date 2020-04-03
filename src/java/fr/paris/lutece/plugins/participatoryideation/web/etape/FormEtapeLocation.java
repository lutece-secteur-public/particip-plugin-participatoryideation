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
package fr.paris.lutece.plugins.participatoryideation.web.etape;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.leaflet.business.GeolocItem;
import fr.paris.lutece.plugins.participatoryideation.business.depositary.DepositaryType;
import fr.paris.lutece.plugins.participatoryideation.business.depositary.DepositaryTypeHome;
import fr.paris.lutece.plugins.participatoryideation.business.proposal.Proposal;
import fr.paris.lutece.plugins.participatoryideation.service.ProposalService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.util.AppLogService;

public class FormEtapeLocation extends AbstractFormEtape
{

    private static final String I18N_ERROR_ARRONDISSEMENT_EMPTY = "participatoryideation.validation.proposal.FormEtapeLocation.Arrondissement.notEmpty";
    private static final String I18N_ERROR_ADRESS_FORMAT = "participatoryideation.validation.proposal.FormEtapeLocation.AdressFormat";
    private static final String I18N_ERROR_ADRESS_NOT_VALID = "participatoryideation.validation.proposal.FormEtapeLocation.AdressNotValid";
    private static final String I18N_ERROR_ADRESS_ARDT_MISMATCH = "participatoryideation.validation.proposal.FormEtapeLocation.ArdtMismatch";
    private static final String I18N_ERROR_COMPLEMENT_EMPTY = "participatoryideation.validation.proposal.FormEtapeLocation.depositary_complement.notEmpty";

    @NotEmpty( message = "#i18n{participatoryideation.validation.proposal.FormEtapeLocation.CodeTheme.notEmpty}" )
    @Size( max = 50, message = "#i18n{participatoryideation.validation.proposal.FormEtapeLocation.CodeTheme.size}" )
    private String _strCodeTheme;

    @NotEmpty( message = "#i18n{participatoryideation.validation.proposal.FormEtapeLocation.localisationTypeNotEmpty}" )
    private String _strLocalisationType;

    private String _strLocalisationArdt;
    private String _strGeojson;
    private String _strAdress;

    @NotEmpty( message = "#i18n{participatoryideation.validation.proposal.FormEtapeLocation.depositaryType.notEmpty}" )
    private String _strDepositaryType;

    @Size( max = 50, message = "#i18n{participatoryideation.validation.proposal.Depositary.size}" )
    private String _strDepositary;

    /**
     * Returns the LocalisationArdt
     * 
     * @return The LocalisationArdt
     */
    public String getLocalisationArdt( )
    {
        return _strLocalisationArdt;
    }

    /**
     * Sets the LocalisationArdt
     * 
     * @param strLocalisationArdt
     *            The LocalisationArdt
     */
    public void setLocalisationArdt( String strLocalisationArdt )
    {
        _strLocalisationArdt = strLocalisationArdt;
    }

    public String getLocalisationType( )
    {
        return _strLocalisationType;
    }

    public void setLocalisationType( String strLocalisationType )
    {
        this._strLocalisationType = strLocalisationType;
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

    public String getGeojson( )
    {
        return _strGeojson;
    }

    public void setGeojson( String _strGeojson )
    {
        this._strGeojson = _strGeojson;
    }

    public List<String> checkValidationErrors( HttpServletRequest request )
    {
        List<String> listErrors = new ArrayList<>( );
        String strComplementType = DepositaryTypeHome.findByCode( getDepositaryType( ) ).getCodeComplementType( );

        if ( !DepositaryType.CODE_COMPLEMENT_TYPE_NONE.equals( strComplementType ) && StringUtils.isBlank( getDepositary( ) ) )
        {
            listErrors.add( I18N_ERROR_COMPLEMENT_EMPTY );
        }

        if ( getLocalisationType( ).equals( Proposal.LOCALISATION_TYPE_ARDT ) && StringUtils.isEmpty( getLocalisationArdt( ) ) )
        {
            listErrors.add( I18N_ERROR_ARRONDISSEMENT_EMPTY );
        }

        // FIXME : This code is waiting for reintegration of geojson mecanism...
        //
        // private static final java.util.regex.Pattern patternAdresseArrondissement = java.util.regex.Pattern.compile(", 75[0-1]([0-2][0-9]) PARIS");
        // if (StringUtils.isNotBlank(getAdress()) && StringUtils.isEmpty(getGeojson())) {
        // listErrors.add(I18N_ERROR_ADRESS_NOT_VALID);
        // }
        //
        // if (StringUtils.isNotEmpty(getGeojson())) {
        // GeolocItem geolocItem = null;
        //
        // try {
        // geolocItem = GeolocItem.fromJSON(this.getGeojson());
        // setAdress(geolocItem.getAddress());
        // Matcher m = patternAdresseArrondissement.matcher(getAdress());
        // m.find();
        // int nArdt;
        // nArdt = Integer.parseInt(m.group(1));
        // String strArdt = ProposalService.getInstance().getArrondissementCode(nArdt);
        // if (getLocalisationType().equals(Proposal.LOCALISATION_TYPE_ARDT) &&
        // StringUtils.isNotEmpty( getLocalisationArdt()) &&
        // (!strArdt.equals(getLocalisationArdt())) ) {
        // listErrors.add(I18N_ERROR_ADRESS_ARDT_MISMATCH);
        // } else {
        // setLocalisationArdt(strArdt);
        // }
        // } catch (IOException e) {
        // listErrors.add(I18N_ERROR_ADRESS_FORMAT);
        // AppLogService.error ( "IdeationApp: malformed data from client: address = " + getGeojson() + "; exeception " + e );
        // }
        // }

        return listErrors;
    }

    public List<String> checkValidationErrorsLocalized( HttpServletRequest request, Locale locale )
    {
        List<String> listErrors = new ArrayList<>( );
        String userUid = "guid";

        if ( SecurityService.getInstance( ).getRegisteredUser( request ) != null )
        {
            LuteceUser user = SecurityService.getInstance( ).getRegisteredUser( request );
            userUid = user.getName( );
        }

        return listErrors;
    }

    public String getAdress( )
    {
        return _strAdress;
    }

    public void setAdress( String _strAdress )
    {
        this._strAdress = _strAdress;
    }

    public String getDepositaryType( )
    {
        return _strDepositaryType;
    }

    public void setDepositaryType( String _strDepositaryType )
    {
        this._strDepositaryType = _strDepositaryType;
    }

    public String getDepositary( )
    {
        return _strDepositary;
    }

    public void setDepositary( String _strConseilQuartier )
    {
        this._strDepositary = _strConseilQuartier;
    }

    public boolean mustCopyDepositary( )
    {
        String strComplementType = DepositaryTypeHome.findByCode( getDepositaryType( ) ).getCodeComplementType( );
        return !DepositaryType.CODE_COMPLEMENT_TYPE_NONE.equals( strComplementType );
    }
}
