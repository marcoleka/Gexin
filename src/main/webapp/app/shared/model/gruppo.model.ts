import { IDoor } from 'app/shared/model//door.model';

export interface IGruppo {
    id?: number;
    nomeGruppo?: string;
    doors?: IDoor[];
    accessoId?: number;
}

export class Gruppo implements IGruppo {
    constructor(public id?: number, public nomeGruppo?: string, public doors?: IDoor[], public accessoId?: number) {}
}
