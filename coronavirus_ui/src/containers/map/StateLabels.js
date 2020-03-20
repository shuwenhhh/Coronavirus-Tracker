import React from 'react';

import Datamap from './DataMap';
//TODO
const includedStates = ['NJ', 'NY'];

const withReduce = () => {
    return includedStates.reduce((result, item) => {
        result[item] = {
            fillKey: 'red',
            clickHandler: (event) => console.log(event.target.dataset.name)
        };
        return result;
    }, {});
};

const StateLabels = (props) => {

    return (
        <div className='Example-map'>
            <Datamap
                scope="usa"
                done={(datamap) => {
                    datamap.svg.selectAll('.datamaps-subunit').on('click', (geography) => {
                        if (includedStates.includes(geography.id)) {
                            props.clickedState(geography.id);
                        }
                    });
                }}
                geographyConfig={{
                    highlightBorderColor: '#bada55',
                    highlightFillColor: '#205493',
                    popupTemplate: (geography) =>
                        `<div class='hoverinfo'>${geography.properties.name}`,
                    highlightBorderWidth: 3,
                }}
                fills={{
                    red: '#0071BC',
                    defaultFill: '#AEB0B5'
                }}
                data={withReduce()}
                labels
            />
        </div>
    );
};

export default StateLabels;
