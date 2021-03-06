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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.participatoryideation.business.proposal.Proposal;
import fr.paris.lutece.plugins.participatoryideation.service.campaign.IdeationCampaignDataProvider;
import fr.paris.lutece.portal.service.datastore.DatastoreService;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.util.AppLogService;

public class FormEtapeTitle extends AbstractFormEtape
{

    private static final String I18N_ERROR_TITRE_MIN_LENGTH = "participatoryideation.validation.proposal.Titre.sizeMin";
    private static final String I18N_ERROR_TITRE_MAX_LENGTH = "participatoryideation.validation.proposal.Titre.sizeMax";

    private static final String I18N_ERROR_FIELD1_EMPTY = "participatoryideation.validation.proposal.Field1.empty";
    private static final String I18N_ERROR_FIELD1_MIN_LENGTH = "participatoryideation.validation.proposal.Field1.sizeMin";
    private static final String I18N_ERROR_FIELD1_MAX_LENGTH = "participatoryideation.validation.proposal.Field1.sizeMax";

    private static final String I18N_ERROR_FIELD2_EMPTY = "participatoryideation.validation.proposal.Field2.empty";
    private static final String I18N_ERROR_FIELD2_MIN_LENGTH = "participatoryideation.validation.proposal.field2.sizeMin";
    private static final String I18N_ERROR_FIELD2_MAX_LENGTH = "participatoryideation.validation.proposal.field2.sizeMax";

    private static final String DSKEY_TITRE_MIN_LENGTH = "participatoryideation.site_property.form.titre.minLength";
    private static final String DSKEY_TITRE_MAX_LENGTH = "participatoryideation.site_property.form.titre.maxLength";

    @NotEmpty( message = "#i18n{participatoryideation.validation.proposal.Titre.notEmpty}" )
    @Size( max = 255, message = "#i18n{participatoryideation.validation.proposal.Titre.size}" )
    private String _strTitre;

    private String _strField1;

    private String _strfield2;

    public String getTitre( )
    {
        return _strTitre;
    }

    public void setTitre( String strTitre )
    {
        this._strTitre = strTitre;
    }

    public String getField1( )
    {
        return _strField1;
    }

    public void setField1( String strField1 )
    {
        this._strField1 = strField1;
    }

    public String getField2( )
    {
        return _strfield2;
    }

    public void setField2( String strfield2 )
    {
        this._strfield2 = strfield2;
    }

    @Override
    public List<String> checkValidationErrorsLocalized( HttpServletRequest request, Proposal proposal, Locale locale )
    {
        List<String> listErrors = new ArrayList<>( );

        // ---------------------------------------------------------------------- Check length of title

        String strMax = DatastoreService.getDataValue( DSKEY_TITRE_MAX_LENGTH, "" );
        if ( !"".equals( strMax ) )
        {
            try
            {
                int nMax = Integer.parseInt( strMax );
                if ( getTitre( ).trim( ).length( ) > nMax )
                {
                    listErrors.add( I18nService.getLocalizedString( I18N_ERROR_TITRE_MAX_LENGTH, new String [ ] {
                            Integer.toString( nMax )
                    }, locale ) );
                }
            }
            catch( NumberFormatException nfe )
            {
                AppLogService.error( "IdeationApp: NumberFormatException when parsing max Titre length from datastore, key " + DSKEY_TITRE_MAX_LENGTH, nfe );
            }
        }

        String strMin = DatastoreService.getDataValue( DSKEY_TITRE_MIN_LENGTH, "" );
        if ( !"".equals( strMin ) )
        {
            try
            {
                int nMin = Integer.parseInt( strMin );
                if ( getTitre( ).trim( ).length( ) < nMin )
                {
                    listErrors.add( I18nService.getLocalizedString( I18N_ERROR_TITRE_MIN_LENGTH, new String [ ] {
                            Integer.toString( nMin )
                    }, locale ) );
                }
            }
            catch( NumberFormatException nfe )
            {
                AppLogService.error( "IdeationApp: NumberFormatException when parsing max Titre length from datastore, key " + DSKEY_TITRE_MAX_LENGTH, nfe );
            }
        }

        // ---------------------------------------------------------------------- Check mandatory and length of optional fields

        String [ ] fieldData = null;

        // TODO : Move these values into field data properties
        int fieldMinLength = 0;
        int fieldMaxLength = 200;

        fieldData = IdeationCampaignDataProvider.getInstance( ).getCampaignFieldData( proposal.getCodeCampaign( ), "field1" );
        if ( "1".contentEquals( fieldData [0] ) )
        {
            if ( "1".contentEquals( fieldData [3] ) && StringUtils.isBlank( getField1( ) ) )
            {
                listErrors.add( I18nService.getLocalizedString( I18N_ERROR_FIELD1_EMPTY, new String [ ] {
                        fieldData [1]
                }, locale ) );
            }
            else
            {
                if ( getField1( ) != null && getField1( ).trim( ).length( ) < fieldMinLength )
                {
                    listErrors.add( I18nService.getLocalizedString( I18N_ERROR_FIELD1_MIN_LENGTH, new String [ ] {
                            fieldData [1], "" + fieldMinLength
                    }, locale ) );
                }

                if ( getField1( ) != null && getField1( ).trim( ).length( ) > fieldMaxLength )
                {
                    listErrors.add( I18nService.getLocalizedString( I18N_ERROR_FIELD1_MAX_LENGTH, new String [ ] {
                            fieldData [1], "" + fieldMaxLength
                    }, locale ) );
                }
            }
        }

        fieldData = IdeationCampaignDataProvider.getInstance( ).getCampaignFieldData( proposal.getCodeCampaign( ), "field2" );
        if ( "1".contentEquals( fieldData [0] ) )
        {
            if ( "1".contentEquals( fieldData [3] ) && StringUtils.isBlank( getField2( ) ) )
            {
                listErrors.add( I18nService.getLocalizedString( I18N_ERROR_FIELD2_EMPTY, new String [ ] {
                        fieldData [1]
                }, locale ) );
            }
            else
            {
                if ( getField2( ) != null && getField2( ).trim( ).length( ) < fieldMinLength )
                {
                    listErrors.add( I18nService.getLocalizedString( I18N_ERROR_FIELD2_MIN_LENGTH, new String [ ] {
                            fieldData [1], "" + fieldMinLength
                    }, locale ) );
                }

                if ( getField2( ) != null && getField2( ).trim( ).length( ) > fieldMaxLength )
                {
                    listErrors.add( I18nService.getLocalizedString( I18N_ERROR_FIELD2_MAX_LENGTH, new String [ ] {
                            fieldData [1], "" + fieldMaxLength
                    }, locale ) );
                }
            }
        }

        return listErrors;
    }

}
