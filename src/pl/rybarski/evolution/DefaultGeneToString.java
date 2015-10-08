/**
* Copyright (c) 2006, Janusz Rybarski
*
* All rights reserved.
* 
* Redistribution and use in source and binary forms,
* with or without modification, are permitted provided
* that the following conditions are met:
*
* Redistributions of source code must retain the above
* copyright notice, this list of conditions and the
* following disclaimer.
*
* Redistributions in binary form must reproduce the
* above copyright notice, this list of conditions
* and the following disclaimer in the documentation
* and/or other materials provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS
* AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
* WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
* A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL
* THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY
* DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
* USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
* HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
* (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
* WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
* OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package pl.rybarski.evolution;

/**
 * DefaultGeneToString is function to create <I>String</I> representaino of the gene
 * @author Janusz Rybarski
 * e-mail: janusz.rybarski AT ae DOT krakow DOT pl
 * @version 1.0 2006/06/18
 */

public class DefaultGeneToString implements IGeneToString{
    
    /** Creates a new instance of DefaultGeneToString */
    public DefaultGeneToString() {
    }
    
    /**
     * Return <I>String</I> representanation of the gene. Gene must by <I>boolean</I> type.<BR>
     * If gene type is <I>true</I> function return <I>1</I>, otherwise <I>0</I>
     * @param geneType gene type
     * @return String representanation of the gene
     */
    public String toString(Object geneType){
        if ((java.lang.Boolean)geneType)
            return "1";
        else
            return "0";
    }
}
