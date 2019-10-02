/*
 * Copyright 1999-2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */ 

package org.apache.commons.workflow.core;


import org.apache.commons.workflow.Context;
import org.apache.commons.workflow.Descriptor;
import org.apache.commons.workflow.Step;


/**
 * <p>Repeatedly evaluate the properties specified by the associated
 * Descriptors, and execute the nested Steps if and only if
 * <strong>ALL</strong> of them evaluate to a negative result.
 * To avoid non-deterministic evaluation
 * stack behavior, all of the specified Descriptors are always
 * evaluated exactly once.</p>
 *
 * @version $Revision$ $Date$
 * @author Craig R. McClanahan
 */

public class WhileNotAnyStep extends WhileStep {


    // ----------------------------------------------------------= Constructors


    /**
     * Construct a default instance of this Step.
     */
    public WhileNotAnyStep() {

        super();

    }


    /**
     * Construct an instance of this Step with the specified identifier.
     *
     * @param id Step identifier
     */
    public WhileNotAnyStep(String id) {

        this(id, null);

    }


    /**
     * Construct a fully configured instance of this Step.
     *
     * @param id Step identifier of this step
     * @param descriptor Initial descriptor to be added
     */
    public WhileNotAnyStep(String id, Descriptor descriptor) {

        super();
        setId(id);
        if (descriptor != null)
            addDescriptor(descriptor);

    }


    // --------------------------------------------------------- Public Methods


    /**
     * Render a string representation of this Step.
     */
    public String toString() {

        StringBuffer sb = new StringBuffer("<core:whileAny");
        if (getId() != null) {
            sb.append(" id=\"");
            sb.append(getId());
            sb.append("\"");
        }
        sb.append(">");
        Descriptor descriptors[] = findDescriptors();
        for (int i = 0; i < descriptors.length; i++)
            sb.append(descriptors[i]);
        Step steps[] = getSteps();
        for (int i = 0; i < steps.length; i++)
            sb.append(steps[i]);
        sb.append("</core:whileAny>");
        return (sb.toString());

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * Evaluate the condition specified by the Descriptors associated with
     * this Block, and return the resulting boolean value.
     *
     * @param context Context within which to evaluate the descriptors
     */
    protected boolean evaluate(Context context) {

        boolean condition = false;
        Descriptor descriptors[] = findDescriptors();
        for (int i = 0; i < descriptors.length; i++) {
            if (descriptors[i].positive(context))
                condition = true;
        }
        return (!condition);

    }


}
