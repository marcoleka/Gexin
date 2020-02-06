import { IGruppo } from 'app/shared/model//gruppo.model';

export interface IDoor {
    id?: number;
    description?: string;
    doorName?: string;
    gruppos?: IGruppo[];
}

export class Door implements IDoor {
    constructor(public id?: number, public description?: string, public doorName?: string, public gruppos?: IGruppo[]) {}
}
