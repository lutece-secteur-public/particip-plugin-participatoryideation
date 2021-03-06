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
package fr.paris.lutece.plugins.participatoryideation.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import au.com.bytecode.opencsv.CSVWriter;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.util.AppException;
import fr.paris.lutece.portal.service.util.AppPropertiesService;

public class CsvUtils
{

    private static final String PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES;
    private static final char PROPERTY_SEPARATEUR_CSV;
    private static final String PROPERTY_ENCODING_CSV = "UTF-8";
    private static final String PARAMETER_HEADER_CSV = ".entete";
    private static final String PARAMETER_SPLIT_CSV = ",";
    private static final String PARAMETER_FIELD_CSV = ".champs";
    public static final String PROPOSALUSERS_PREFIX_CSV = "PROPOSALUSERS";
    public static final String PARAMETER_ID_USER_FIELD_CSV = "id_user";

    private static ArrayList<String> _exportKeyList;
    private static Map<String, Integer> _headersMap;
    private static String _strKeyPrefixCsv = "";

    static
    {
        PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES = AppPropertiesService.getProperty( "participatoryideation.csv.configuration.path" );
        PROPERTY_SEPARATEUR_CSV = AppPropertiesService.getProperty( "participatoryideation.csv.separator", ";" ).charAt( 0 );
    }

    private static void getValuesFromCsv( String key )
    {
        // Load csv property file
        InputStream isCsvProperty = CsvUtils.class.getClassLoader( ).getResourceAsStream( PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES );
        if ( isCsvProperty == null )
        {
            throw new AppException( "Fichier " + PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES + " non trouvé." );
        }
        else
        {
            try
            {
                Properties csvProperty = new Properties( );
                csvProperty.load( isCsvProperty );
                _exportKeyList = new ArrayList<String>( );
                _headersMap = new HashMap<String, Integer>( );

                // Retrieve the fields
                String strFieldsList = csvProperty.getProperty( key + PARAMETER_FIELD_CSV );
                String [ ] fieldsArray = strFieldsList.split( PARAMETER_SPLIT_CSV );

                int i = 0;
                for ( String field : fieldsArray )
                {
                    _headersMap.put( field, i );
                    _exportKeyList.add( field );
                    ++i;
                }

            }
            catch( IOException e )
            {
                throw new AppException( "Problème lors de l'édition du fichier CSV : " + e.getMessage( ), e );
            }
        }
    }

    public static List<String> getPrefKeys( String key )
    {
        if ( !key.equals( _strKeyPrefixCsv ) )
        {
            getValuesFromCsv( key );
        }

        return _exportKeyList;
    }

    public static Map<String, Integer> getHeaderLineOrder( String key )
    {
        if ( !key.equals( _strKeyPrefixCsv ) )
        {
            getValuesFromCsv( key );
        }

        return _headersMap;
    }

    /**
     * Ecrit sur la sortie passée en paramètre, les lignes de csv. Attention : ne gère pas l'écriture des en-têtes (content type par exemple) ni la fermeture du
     * flux de sortie.
     * 
     * @param cle
     * @param <R>
     *            type du DTO résultat
     * @param businessDTO
     * @param listeResultat
     * @param out
     */
    public static void writeCsv( String key, List<ArrayList<String>> resultList, OutputStream out, Locale locale )
    {
        if ( resultList != null )
        {
            // Load csv property file
            InputStream isCsvProperty = CsvUtils.class.getClassLoader( ).getResourceAsStream( PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES );
            if ( isCsvProperty == null )
            {
                throw new AppException( "Fichier " + PROPERTY_RESOURCES_LIBRARY_CSV_PROPERTIES + " non trouvé." );
            }
            else
            {
                try
                {
                    Properties csvProperty = new Properties( );
                    csvProperty.load( isCsvProperty );
                    ;
                    CSVWriter csvWriter = new CSVWriter( new OutputStreamWriter( out, PROPERTY_ENCODING_CSV ), PROPERTY_SEPARATEUR_CSV );

                    // Retrieve the headers
                    String strHeadersList = csvProperty.getProperty( key + PARAMETER_HEADER_CSV );
                    String [ ] headersArray = strHeadersList.split( PARAMETER_SPLIT_CSV );

                    for ( int i = 0; i < headersArray.length; i++ )
                    {
                        headersArray [i] = I18nService.getLocalizedString( headersArray [i], locale );

                    }
                    csvWriter.writeNext( headersArray );

                    if ( !resultList.isEmpty( ) )
                    {
                        // Retrieve the fields
                        String strFieldsList = csvProperty.getProperty( key + PARAMETER_FIELD_CSV );
                        String [ ] fieldsArray = strFieldsList.split( PARAMETER_SPLIT_CSV );

                        for ( ArrayList<String> userInfos : resultList )
                        {
                            List<String> ligneCsv = new ArrayList<String>( );
                            for ( int i = 0; i < userInfos.size( ); i++ )
                            {
                                ligneCsv.add( userInfos.get( i ) );
                            }
                            String [ ] stockArr = new String [ ligneCsv.size( )];
                            csvWriter.writeNext( ligneCsv.toArray( stockArr ) );
                        }
                    }

                    csvWriter.flush( );
                    csvWriter.close( );

                }
                catch( IOException e )
                {
                    throw new AppException( "Problème lors de l'édition du fichier CSV : " + e.getMessage( ), e );
                }
            }

        }
    }
}
